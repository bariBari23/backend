package store.baribari.demo.domain.store.dto

import store.baribari.demo.domain.store.entity.LikeStore

data class ShowLikeResponseDto(
    val likeList: List<StoreIdAndNameDto>,
) {
    companion object {
        fun fromLikeStores(likeStores: List<LikeStore>): ShowLikeResponseDto =
            ShowLikeResponseDto(
                likeList = likeStores.map { StoreIdAndNameDto.fromStore(it.store) },
            )
    }
}
