package store.baribari.demo.common.exception

import store.baribari.demo.common.enums.ErrorCode

open class InternalServiceException(val errorCode: ErrorCode, val log: String) : RuntimeException()
