package store.baribari.demo.common.exception

import store.baribari.demo.common.enums.ErrorCode

class UnAuthorizedException(
    errorCode: ErrorCode,
    log: String,
) : BizException(errorCode, log)
