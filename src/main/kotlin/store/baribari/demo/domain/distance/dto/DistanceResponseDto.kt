package store.baribari.demo.domain.distance.dto

import store.baribari.demo.common.util.calculateDistanceInMeter
import store.baribari.demo.domain.store.entity.Store
import store.baribari.demo.domain.user.entity.User

data class DistanceResponseDto(
    val storeId: Long,
    val storeName: String,
    val storeAddress: String,
    val storeDistance: Double,
) {
    companion object {
        fun storeAndUser(
            user: User,
            store: Store,
        ): DistanceResponseDto =
            DistanceResponseDto(
                storeId = store.id!!,
                storeName = store.name,
                storeAddress = store.address,
                storeDistance = calculateDistanceInMeter(user.position, store.position)!!,
            )
    }
}
