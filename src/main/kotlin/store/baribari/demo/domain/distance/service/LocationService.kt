package store.baribari.demo.domain.distance.service

import store.baribari.demo.domain.distance.dto.AllDistanceResponseDto
import store.baribari.demo.domain.distance.dto.DistanceResponseDto

interface LocationService {
    // TODO: 자신의 위치를 근거로 제시된 가게의 위치를 계산하는 기능
    fun calOneDistance(
        username: String,
        storeId: Long,
    ): DistanceResponseDto

    // TODO: 자신의 주변 가게를 반환하는 기능 -> 디비 전체를 탐색?
    fun calAllDistance(username: String): AllDistanceResponseDto
}
