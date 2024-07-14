package store.baribari.demo.domain.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import store.baribari.demo.common.enums.Role
import store.baribari.demo.domain.user.entity.User
import java.util.Collections

class UserPrincipal(
    private val userId: String,
    private val password: String,
    private val providerType: ProviderType,
    private val roleType: Role,
    private val authorities: MutableCollection<GrantedAuthority>,
) : OAuth2User,
    UserDetails,
    OidcUser {
    private lateinit var attributes: MutableMap<String, Any>

    override fun getName(): String = userId

    override fun getAttributes(): MutableMap<String, Any> = attributes

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun getPassword(): String = password

    override fun getUsername(): String = userId

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun getClaims(): MutableMap<String, Any>? = null

    override fun getUserInfo(): OidcUserInfo? = null

    override fun getIdToken(): OidcIdToken? = null

    companion object {
        fun create(user: User): UserPrincipal =
            UserPrincipal(
                user.id.toString(),
                user.password,
                user.providerType,
                Role.ROLE_STORE,
                Collections.singletonList(SimpleGrantedAuthority(Role.ROLE_STORE.name)),
            )

        fun create(
            user: User,
            attributes: MutableMap<String, Any>,
        ): UserPrincipal {
            val userPrincipal = create(user)
            userPrincipal.attributes = attributes

            return userPrincipal
        }
    }
}
