package store.baribari.demo.domain.distance.dto

import store.baribari.demo.domain.store.entity.Store
import store.baribari.demo.domain.user.entity.User

data class AllDistanceResponseDto(
    val distanceList: List<DistanceResponseDto>,
) {
    companion object {
        fun storeListToAllDistanceResponseDto(
            user: User,
            stores: List<Store>,
        ): AllDistanceResponseDto =
            AllDistanceResponseDto(
                distanceList =
                stores.map {
                    DistanceResponseDto.storeAndUser(
                        user = user,
                        store = it,
                    )
                },
            )
    }
}
