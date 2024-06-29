package store.baribari.demo.common.exception

import store.baribari.demo.common.enums.ErrorCode

class OAuthProviderMissMatchException(log: String) : BizException(ErrorCode.PROVIDER_MISS_MATCH, log)
