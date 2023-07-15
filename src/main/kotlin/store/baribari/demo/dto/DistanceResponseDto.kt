package store.baribari.demo.dto

import store.baribari.demo.common.util.calculateDistanceInMeter
import store.baribari.demo.model.Store
import store.baribari.demo.model.User

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
        ): DistanceResponseDto {
            return DistanceResponseDto(
                storeId = store.id!!,
                storeName = store.name,
                storeAddress = store.address,
                storeDistance = calculateDistanceInMeter(user.position, store.position)!!,
            )
        }
    }
}
