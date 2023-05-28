package store.baribari.demo.common.exception

import store.baribari.demo.common.enums.ErrorCode

class ConditionConflictException(errorCode: ErrorCode, log: String) : BizException(ErrorCode.CONDITION_NOT_FULFILLED, log)
