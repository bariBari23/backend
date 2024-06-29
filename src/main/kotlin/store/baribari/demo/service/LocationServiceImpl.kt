package store.baribari.demo.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.dto.AllDistanceResponseDto
import store.baribari.demo.dto.DistanceResponseDto
import store.baribari.demo.repository.StoreRepository
import store.baribari.demo.repository.UserRepository

@Service
class LocationServiceImpl(
    private val storeRepository: StoreRepository,
    private val userRepository: UserRepository,
) : LocationService {

    @Transactional(readOnly = true)
    override fun calOneDistance(
        username: String,
        storeId: Long
    ): DistanceResponseDto {
        val user = userRepository.findByEmailFetchPosition(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")
        val store = storeRepository.findByIdOrNull(storeId)
            ?: throw EntityNotFoundException("$storeId 라는 가게는 존재하지 않습니다.")

        return DistanceResponseDto.storeAndUser(
            user = user,
            store = store,
        )
    }

    @Transactional(readOnly = true)
    override fun calAllDistance(
        username: String
    ): AllDistanceResponseDto {
        val user = userRepository.findByEmailFetchPosition(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")
        val stores = storeRepository.findAll()
        val distanceResponseDtoList = mutableListOf<DistanceResponseDto>()

        return AllDistanceResponseDto.storeListToAllDistanceResponseDto(
            user = user,
            stores = stores,
        )
    }
}
