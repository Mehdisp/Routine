package ir.mehdisp.routine.api

import android.util.Log
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class HttpLoggingInterceptor(enabled: Boolean) : Interceptor {

    private val TAG = "HttpLoggingInterceptor"
    private val level: Level = if (enabled) Level.BODY else Level.NONE

    enum class Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    private fun log(body: String) {
        Log.d(TAG, body)
    }

    @Throws(IOException::class, IllegalStateException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val level = this.level

        val request = chain.request()
        if (level == Level.NONE) {
            return chain.proceed(request)
        }

        val logBody = level == Level.BODY
        val logHeaders = logBody || level == Level.HEADERS

        val requestBody = request.body()
        val hasRequestBody = requestBody != null

        val connection = chain.connection()
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1
        var requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody?.contentLength() + "-byte body)"
        }

        log(requestStartMessage)

        if (logHeaders) {
            if (hasRequestBody) {
                if (requestBody?.contentType() != null) {
                    log("Content-Type: " + requestBody.contentType())
                }
                if (requestBody?.contentLength() != -1L) {
                    log("Content-Length: " + requestBody?.contentLength())
                }
            }

            val headers = request.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(name, ignoreCase = true)
                    && !"Content-Length".equals(name, ignoreCase = true)) {
                    log(name + ": " + headers.value(i))
                }

                i++
            }

            if (!logBody || !hasRequestBody) {
                log("--> END " + request.method())
            } else if (bodyEncoded(request.headers())) {
                log("--> END " + request.method() + " (encoded body omitted)")
            } else {
                val buffer = Buffer()
                requestBody?.writeTo(buffer)

                var charset = UTF8
                val contentType = requestBody?.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                log("")
                if (isPlaintext(buffer)) {
                    log(buffer.readString(charset))
                    log("--> END " + request.method()
                            + " (" + requestBody?.contentLength() + "-byte body)"
                    )
                } else {
                    log(
                        "--> END " + request.method() + " (binary "
                                + requestBody?.contentLength() + "-byte body omitted)"
                    )
                }
            }
        }

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            log("<-- HTTP FAILED: $e")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        val contentLength = responseBody?.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"
        log(
            "<-- " + response.code() + ' ' + response.message() + ' '
                    + response.request().url() + " (" + tookMs + "ms" + (if (!logHeaders)
                ", "
                        + bodySize + " body"
            else
                "") + ')'
        )

        if (logHeaders) {
            val headers = response.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                log(headers.name(i) + ": " + headers.value(i))
                i++
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                log("<-- END HTTP")
            } else if (bodyEncoded(response.headers())) {
                log("<-- END HTTP (encoded body omitted)")
            } else {
                val source = responseBody!!.source()
                try {
                    source.request(Long.MAX_VALUE)
                } catch (e: Exception) { }

                val buffer = source.buffer()

                var charset =
                    UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                if (!isPlaintext(buffer)) {
                    log("")
                    log("<-- END HTTP (binary " + buffer.size + "-byte body omitted)")
                    return response
                }

                if (contentLength != 0L) {
                    log("")
                    log(buffer.clone().readString(charset))
                }

                log("<-- END HTTP (" + buffer.size + "-byte body)")
            }
        }

        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

    companion object {

        private val UTF8 = Charset.forName("UTF-8")

        internal fun isPlaintext(buffer: Buffer): Boolean {
            try {
                val prefix = Buffer()
                val byteCount = if (buffer.size < 64) buffer.size else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                return true
            } catch (e: EOFException) {
                return false
            }

        }
    }
}
