package store.baribari.demo.common.exception

import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.exception.BizException

class EntityNotFoundException(log: String) : BizException(ErrorCode.NOT_FOUND_ENTITY, log)