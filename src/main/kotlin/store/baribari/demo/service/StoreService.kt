package store.baribari.demo.service

import store.baribari.demo.dto.ShowLikeResponseDto
import store.baribari.demo.dto.StoreInfoResponseDto

interface StoreService {
    fun storeInfo(
        userEmail: String?,
        storeId: Long,
    ) : StoreInfoResponseDto

    fun storeLike(
        userEmail: String,
        storeId: Long,
    ): Long

    fun storeLikeCancel(
        userEmail: String,
        storeId: Long,
    ): Long

    fun showLike(
        userEmail: String,
    ): ShowLikeResponseDto
}
