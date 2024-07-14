package store.baribari.demo.common.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import store.baribari.demo.domain.auth.UserPrincipal
import store.baribari.demo.domain.user.repository.UserRepository
import javax.persistence.EntityNotFoundException

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user =
            userRepository.findByEmail(username)
                ?: throw EntityNotFoundException("$username 을 가진 유저는 존재하지 않습니다.")

        return UserPrincipal.create(user)
    }
}
