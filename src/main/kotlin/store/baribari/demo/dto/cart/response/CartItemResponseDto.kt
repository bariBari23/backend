package store.baribari.demo.dto.cart.response

import store.baribari.demo.model.cart.CartItem

data class CartItemResponseDto(
    val id: Long,
    val name: String,
    val price: Int,
    val quantity: Int,
    val total: Int,
) {
    companion object {
        fun fromCartItem(cartItem: CartItem): CartItemResponseDto {
            return CartItemResponseDto(
                id = cartItem.dosirak.id!!,
                name = cartItem.dosirak.name,
                price = cartItem.dosirak.price,
                quantity = cartItem.count,
                total = cartItem.dosirak.price * cartItem.count,
            )
        }
    }
}

