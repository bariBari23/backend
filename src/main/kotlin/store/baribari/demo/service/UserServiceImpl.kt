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
        username: String,
        latitude: Double,
        longitude: Double,
    ): Position {
        val user = userRepository.findByEmailFetchPosition(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        user.position.latitude = latitude
        user.position.longitude = longitude

        return Position(latitude, longitude)
    }


}