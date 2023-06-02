package store.baribari.demo.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.exception.ConditionConflictException
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.dto.ShowLikeResponseDto
import store.baribari.demo.dto.StoreInfoResponseDto
import store.baribari.demo.model.LikeStore
import store.baribari.demo.repository.LikeStoreRepository
import store.baribari.demo.repository.StoreRepository
import store.baribari.demo.repository.UserRepository

@Service
class StoreServiceImpl(
    private val userRepository: UserRepository,
    private val storeRepository: StoreRepository,
    private val likeStoreRepository: LikeStoreRepository
) : StoreService {
    override fun storeInfo(
        username: String?,
        storeId: Long,
    ): StoreInfoResponseDto {
        val store = storeRepository.findByIdOrNull(storeId)
            ?: throw EntityNotFoundException("$storeId 번 가게는 존재하지 않습니다.")

        val user = username?.let {
            userRepository.findByEmail(username)
                ?: throw EntityNotFoundException("$username 에 해당하는 유저는 존재하지 않습니다.")
        }

        val liked: Boolean = user?.let {
            likeStoreRepository.findByUserAndStore(it, store) != null
        } ?: false


        return StoreInfoResponseDto.fromStore(store, liked)
    }

    override fun storeLike(
        username: String,
        storeId: Long,
    ): Long {
        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 에 해당하는 유저는 존재하지 않습니다.")

        val store = storeRepository.findByIdOrNull(storeId)
            ?: throw EntityNotFoundException("$storeId 번 가게는 존재하지 않습니다.")

        likeStoreRepository.findByUserAndStore(user, store)?.let {
            throw ConditionConflictException(ErrorCode.CONDITION_NOT_FULFILLED, "이미 추천을 했습니다.")
        }

        val likeStore = LikeStore(user = user, store = store)
        likeStoreRepository.save(likeStore)
        store.likeStore(likeStore)

        return likeStore.id!!
    }

    override fun storeLikeCancel(
        username: String,
        storeId: Long,
    ): Long {
        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 에 해당하는 유저는 존재하지 않습니다.")

        val store = storeRepository.findByIdOrNull(storeId)
            ?: throw EntityNotFoundException("$storeId 번 가게는 존재하지 않습니다.")

        likeStoreRepository.findByUserAndStore(user, store)?.let {
            store.cancelLikeStore(it)
            likeStoreRepository.delete(it)
        } ?: throw ConditionConflictException(ErrorCode.CONDITION_NOT_FULFILLED, "추천을 하지 않았습니다.")

        return store.id!!
    }

    override fun showLike(
        username: String,
    ): ShowLikeResponseDto {
        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 에 해당하는 유저는 존재하지 않습니다.")

        val likeStores = likeStoreRepository.findByUserFetchStore(user)

        return ShowLikeResponseDto.fromLikeStores(likeStores)
    }
}