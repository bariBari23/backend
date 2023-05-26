package store.baribari.demo.common.exception

import store.baribari.demo.common.enums.ErrorCode

open class BizException(val errorCode: ErrorCode, val log: String) : RuntimeException()
