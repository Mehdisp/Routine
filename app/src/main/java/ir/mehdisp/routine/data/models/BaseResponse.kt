package ir.mehdisp.routine.data.models

import com.squareup.moshi.Json

abstract class BaseResponse<T> {
    @field:Json(name = "reqStatus") var reqStatus: RequestStatus? = null
    @field:Json(name = "result") var result: T? = null
}

data class RequestStatus(
    @field:Json(name = "statusCode") val statusCode: Int,
    @field:Json(name = "message") val message: String?
)