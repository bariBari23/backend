package store.baribari.demo.domain.store.service

import store.baribari.demo.domain.store.dto.ShowLikeResponseDto
import store.baribari.demo.domain.store.dto.StoreInfoResponseDto

interface StoreService {
    fun storeInfo(
        userEmail: String?,
        storeId: Long,
    ): StoreInfoResponseDto

    fun storeLike(
        userEmail: String,
        storeId: Long,
    ): Long

    fun storeLikeCancel(
        userEmail: String,
        storeId: Long,
    ): Long

    fun showLike(userEmail: String): ShowLikeResponseDto
}
