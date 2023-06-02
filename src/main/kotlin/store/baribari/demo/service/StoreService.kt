package store.baribari.demo.service

import store.baribari.demo.dto.ShowLikeResponseDto
import store.baribari.demo.dto.StoreInfoResponseDto

interface StoreService {
    fun storeInfo(
        username: String?,
        storeId: Long,
    ) : StoreInfoResponseDto

    fun storeLike(
        username: String,
        storeId: Long,
    ): Long

    fun storeLikeCancel(
        username: String,
        storeId: Long,
    ): Long

    fun showLike(
        username: String,
    ): ShowLikeResponseDto
}
