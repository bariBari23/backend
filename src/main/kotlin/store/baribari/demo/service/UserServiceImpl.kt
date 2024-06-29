package store.baribari.demo.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.model.embed.Position
import store.baribari.demo.repository.UserRepository

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    @Transactional
    override fun setLocation(
        userEmail: String,
        position: Position,
    ): Position {
        val user = userRepository.findByEmailFetchPosition(userEmail)
            ?: throw EntityNotFoundException("$userEmail 이라는 유저는 존재하지 않습니다.")

        user.position.latitude = position.latitude
        user.position.longitude = position.longitude

        return Position(position.latitude, position.longitude)
    }

    override fun getLocation(
        userEmail: String
    ): Position {
        val user = userRepository.findByEmailFetchPosition(userEmail)
            ?: throw EntityNotFoundException("$userEmail 이라는 유저는 존재하지 않습니다.")

        return user.position
    }
}
