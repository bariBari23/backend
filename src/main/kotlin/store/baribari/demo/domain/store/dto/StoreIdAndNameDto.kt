package store.baribari.demo.domain.store.dto

import store.baribari.demo.domain.store.entity.Store

data class StoreIdAndNameDto(
    val storeId: Long,
    val storeName: String,
    val mainImageUrl: String,
    val storeImageList: List<String>,
) {
    companion object {
        fun fromStore(store: Store): StoreIdAndNameDto =
            StoreIdAndNameDto(
                storeId = store.id!!,
                storeName = store.name,
                mainImageUrl = store.mainImageUrl,
                storeImageList = store.storeImageList,
            )
    }
}
