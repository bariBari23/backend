package store.baribari.demo.common.exception

import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.exception.BizException

class UnAuthorizedException(
    errorCode: ErrorCode,
    log: String,
) : BizException(errorCode, log)

