package store.baribari.demo.dto

import store.baribari.demo.model.Store
import store.baribari.demo.model.User

data class AllDistanceResponseDto(
    val distanceList: List<DistanceResponseDto>
) {
    companion object {
        fun storeListToAllDistanceResponseDto(
            user: User,
            stores: List<Store>,
        ): AllDistanceResponseDto {
            return AllDistanceResponseDto(
                distanceList = stores.map {
                    DistanceResponseDto.storeAndUser(
                        user = user,
                        store = it,
                    )
                }
            )
        }
    }
}
