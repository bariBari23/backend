package store.baribari.demo.dto

import store.baribari.demo.model.Store

data class StoreIdAndNameDto(
    val storeId: Long,
    val storeName: String,
) {
    companion object {
        fun fromStore(store: Store): StoreIdAndNameDto {
            return StoreIdAndNameDto(
                storeId = store.id!!,
                storeName = store.name,
            )
        }
    }
}
