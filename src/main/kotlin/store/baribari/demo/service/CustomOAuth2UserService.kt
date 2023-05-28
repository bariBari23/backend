package store.baribari.demo.service


import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import store.baribari.demo.auth.OAuth2UserInfo
import store.baribari.demo.auth.OAuth2UserInfoFactory
import store.baribari.demo.auth.ProviderType
import store.baribari.demo.auth.UserPrincipal
import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.exception.InternalServiceException
import store.baribari.demo.common.exception.OAuthProviderMissMatchException
import store.baribari.demo.model.User
import store.baribari.demo.model.cart.Cart
import store.baribari.demo.repository.UserRepository
import java.util.*

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
) : DefaultOAuth2UserService() {
    // 받아온 token을 분석해서 필요한 정보를 넘겨주는 역할
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val user = super.loadUser(userRequest)

        return runCatching {
            process(userRequest!!, user)
        }.onFailure {
            if (it is OAuthProviderMissMatchException) {
                throw it
            }
            throw InternalServiceException(ErrorCode.SERVER_ERROR, it.message.toString())
        }.getOrThrow()
    }

    private fun process(userRequest: OAuth2UserRequest, user: OAuth2User): OAuth2User {
        val providerType =
            ProviderType.valueOf(userRequest.clientRegistration.registrationId.uppercase(Locale.getDefault()))

        val accessToken = userRequest.accessToken.tokenValue

        val attributes = user.attributes.toMutableMap()
        // 유저가 존재하거나 존재하지 않으면 UserPrincipal을 생성한다.
        return UserPrincipal.create(getOrCreateUser(providerType, attributes), attributes)
    }

    private fun getOrCreateUser(
        providerType: ProviderType,
        attributes: MutableMap<String, Any>,
    ): User {
        val userInfo = OAuth2UserInfoFactory.getOauth2UserInfo(providerType, attributes)

        val savedUser = userRepository.findByEmail(userInfo.getEmail())
            ?: userRepository.findUserByProviderId(userInfo.getId())

        return savedUser ?: createUser(userInfo, providerType)
    }

    private fun createUser(userInfo: OAuth2UserInfo, providerType: ProviderType): User {
        val user = User(
            email = userInfo.getEmail(),
            providerId = userInfo.getId(),
            profileImageUrl = userInfo.getImageUrl(),
            userCart = Cart()
        )
        println("user : $user")
        println("user info: " + userInfo.getId())
        println("provider type: $providerType")
        println("user email: " + userInfo.getEmail())

        return userRepository.saveAndFlush(user)
    }
}
