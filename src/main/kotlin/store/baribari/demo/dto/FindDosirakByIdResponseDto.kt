package store.baribari.demo.dto

import store.baribari.demo.model.User
import store.baribari.demo.model.menu.Dosirak

data class FindDosirakByIdResponseDto(
    val id: Long,
    val name: String,
    val price: Int,
    val gram: Int,
    val stock: Int,
    val mainImageUrl: String = "",
    val banchanList: List<BanchanNameAndGramDto> = listOf(),
    val photoList: List<String> = listOf(),
    val storeId: Long,
    val storeName: String,
    val likedStore: Boolean,
    val fromWhere: String,
    val mealTimes: Int,
) {
    companion object {
        fun fromDosirakToDto(dosirak: Dosirak, user: User?): FindDosirakByIdResponseDto {
            return FindDosirakByIdResponseDto(
                id = dosirak.id!!,
                name = dosirak.name,
                price = dosirak.price,
                gram = dosirak.gram,
                stock = dosirak.stock,
                mainImageUrl = dosirak.mainImageUrl,
                photoList = dosirak.dosirakImageList,
                banchanList = dosirak.dosirakBanchanList.map { BanchanNameAndGramDto.fromBanchanToDto(it.banchan) },
                storeId = dosirak.store.id!!,
                storeName = dosirak.store.name,
                likedStore = user?.likeStoreList?.map { it.store }?.contains(dosirak.store) ?: false,
                fromWhere = dosirak.fromWhere,
                mealTimes = dosirak.mealTimes,
            )
        }
    }
}
