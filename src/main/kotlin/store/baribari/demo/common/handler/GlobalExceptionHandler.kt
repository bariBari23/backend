package store.baribari.demo.common.handler

import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.exception.BizException
import store.baribari.demo.common.exception.InternalServiceException
import store.baribari.demo.common.util.log
import store.baribari.demo.dto.common.ApiResponse

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(value = [BizException::class])
    fun handlingBizException(bizException: BizException): ResponseEntity<ApiResponse<String>> {
        log.error(bizException.log)
        return ResponseEntity.status(bizException.errorCode.code)
            .body(ApiResponse.fail(bizException.errorCode.message))
    }

    @ExceptionHandler(
        value = [
            IllegalArgumentException::class,
            MethodArgumentTypeMismatchException::class,
            HttpRequestMethodNotSupportedException::class,
        ],
    )
    fun handlingBizException(exception: Exception): ResponseEntity<ApiResponse<String>> {
        log.error(exception.message)
        return ResponseEntity.status(ErrorCode.CONDITION_NOT_FULFILLED.code)
            .body(ApiResponse.fail(ErrorCode.CONDITION_NOT_FULFILLED.message))
    }

    @ExceptionHandler(value = [InternalServiceException::class])
    fun handlingInternalServiceException(internalServiceException: InternalServiceException):
        ResponseEntity<ApiResponse<String>> {
        log.error(internalServiceException.log)

        return ResponseEntity.status(internalServiceException.errorCode.code)
            .body(ApiResponse.error(internalServiceException.errorCode.message))
    }

    @ExceptionHandler(value = [Exception::class])
    fun handlingException(exception: Exception): ResponseEntity<ApiResponse<String>> {
        log.error(exception.message)

        if (exception is AccessDeniedException) {
            return ResponseEntity.status(ErrorCode.FORBIDDEN.code)
                .body(ApiResponse.fail(ErrorCode.FORBIDDEN.message))
        }

        return ResponseEntity.status(ErrorCode.SERVER_ERROR.code)
            .body(ApiResponse.error(ErrorCode.SERVER_ERROR.message))
    }
}
