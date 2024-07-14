package store.baribari.demo.common.dto

import com.fasterxml.jackson.annotation.JsonInclude

private const val SUCCESS = "success"
private const val FAIL = "fail"
private const val ERROR = "error"

@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiResponse<T> private constructor(
    val status: String,
    val data: T? = null,
    val message: String? = null,
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> = ApiResponse(SUCCESS, data)

        fun <T> fail(reason: T): ApiResponse<T> = ApiResponse(FAIL, reason)

        fun <T> error(message: String): ApiResponse<T> = ApiResponse(ERROR, message = message)
    }
}
