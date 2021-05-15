package ir.mehdisp.routine.data.models

import com.squareup.moshi.Json


data class Resource<out T>(
    @Json( name = "status") val status: Status,
    @Json( name = "data") val data: T?,
    @Json( name = "message") val message: String?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

    fun isSuccess() = status == Status.SUCCESS
    fun isLoading() = status == Status.LOADING
    fun isFailed() = status == Status.ERROR

}