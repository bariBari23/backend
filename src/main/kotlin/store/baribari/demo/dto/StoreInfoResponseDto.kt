package store.baribari.demo.dto

import store.baribari.demo.common.util.calculateDistanceInMeter
import store.baribari.demo.model.Review
import store.baribari.demo.model.Store
import store.baribari.demo.model.User
import store.baribari.demo.model.embed.Position

data class StoreInfoResponseDto(
    val storeId: Long,
    val storeName: String,
    val storeAddress: String,
    val phoneNumber: String,
    val businessName: String,
    val businessNumber: String,
    val mainImage: String,
    val storeImageList: List<String>,
    val description: String,
    val fromWhere: String,
    val clean: String,
    val dayList: String,
    val offDay: String,
    val liked: Boolean = false,
    val position: Position,
    val distanceMeter: Double?,
    val reviewMean: Double,
) {
    companion object {
        fun fromStore(
            user: User?,
            store: Store,
            liked: Boolean,
            reviews: List<Review>,
        ): StoreInfoResponseDto {
            return StoreInfoResponseDto(
                storeId = store.id!!,
                storeName = store.name,
                storeAddress = store.address,
                phoneNumber = store.phoneNumber,
                businessName = store.businessName,
                businessNumber = store.businessNumber,
                mainImage = store.mainImageUrl,
                storeImageList = store.storeImageList,
                description = store.description,
                fromWhere = store.fromWhere,
                clean = store.clean,
                dayList = store.dayList,
                offDay = store.offDay,
                liked = liked,
                position = store.position,
                distanceMeter = calculateDistanceInMeter(user?.position, store.position),
                reviewMean = if (reviews.isEmpty()) 0.0 else reviews.sumOf { it.rating }.toDouble() / reviews.size
            )
        }
    }
}
