package store.baribari.demo.domain.cart.dto.response

import store.baribari.demo.domain.cart.domain.CartItem

data class CartItemResponseDto(
    val id: Long,
    val name: String,
    val price: Int,
    val quantity: Int,
    val total: Int,
    val dosirakMainImageUrl: String,
    val storeName: String,
    val storeMainImageUrl: String,
) {
    companion object {
        fun fromCartItem(cartItem: CartItem): CartItemResponseDto =
            CartItemResponseDto(
                id = cartItem.dosirak.id!!,
                name = cartItem.dosirak.name,
                price = cartItem.dosirak.price,
                quantity = cartItem.count,
                total = cartItem.dosirak.price * cartItem.count,
                dosirakMainImageUrl = cartItem.dosirak.mainImageUrl,
                storeName = cartItem.dosirak.store.name,
                storeMainImageUrl = cartItem.dosirak.store.mainImageUrl,
            )
    }
}
