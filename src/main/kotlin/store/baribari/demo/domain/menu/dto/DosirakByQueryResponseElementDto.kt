package store.baribari.demo.domain.menu.dto

import store.baribari.demo.domain.menu.entity.Dosirak
import store.baribari.demo.domain.user.entity.User

data class DosirakByQueryResponseElementDto(
    val id: Long,
    val name: String,
    val price: Int = 0,
    val stock: Int = 0,
    val mainImageUrl: String = "",
    val likedStore: Boolean,
    val banchanList: List<String> = listOf(),
    val storeName: String,
) {
    companion object {
        fun createDtoFromEntity(
            dosirak: Dosirak,
            user: User?,
        ): DosirakByQueryResponseElementDto =
            DosirakByQueryResponseElementDto(
                id = dosirak.id!!,
                name = dosirak.name,
                price = dosirak.price,
                stock = dosirak.stock,
                mainImageUrl = dosirak.mainImageUrl,
                likedStore = user?.likeStoreList?.map { it.store }?.contains(dosirak.store) ?: false,
                banchanList = dosirak.dosirakBanchanList.map { it.banchan.name },
                storeName = dosirak.store.name,
            )
    }
}
