package store.baribari.demo.dto

import store.baribari.demo.model.LikeStore

data class ShowLikeResponseDto(
    val likeList: List<StoreIdAndNameDto>,
) {
    companion object {
        fun fromLikeStores(likeStores: List<LikeStore>): ShowLikeResponseDto {
            return ShowLikeResponseDto(
                likeList = likeStores.map { StoreIdAndNameDto.fromStore(it.store) },
            )
        }
    }
}
