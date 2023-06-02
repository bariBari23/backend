package store.baribari.demo.dto

import store.baribari.demo.model.Store

data class StoreInfoResponseDto(
    val storeId: Long,
    val storeName: String,
    val storeAddress: String,
    val phoneNumber: String,
    val businessName: String,
    val businessNumber: String,
    val address: String,
    val mainImage: String,
    val storeImageList: List<String>,
    val description: String,
    val clean: String,
    val dayList: String,
    val offDay: String,
    val liked: Boolean = false,
) {
    companion object {
        fun fromStore(store: Store, liked: Boolean): StoreInfoResponseDto {
            return StoreInfoResponseDto(
                storeId = store.id!!,
                storeName = store.name,
                storeAddress = store.address,
                phoneNumber = store.phoneNumber,
                businessName = store.businessName,
                businessNumber = store.businessNumber,
                address = store.address,
                mainImage = store.mainImage,
                storeImageList = store.storeImageList,
                description = store.description,
                clean = store.clean,
                dayList = store.dayList,
                offDay = store.offDay,
                liked = liked,
            )
        }
    }
}
