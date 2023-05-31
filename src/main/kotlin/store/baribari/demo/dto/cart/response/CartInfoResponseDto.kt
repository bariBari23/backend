package store.baribari.demo.dto.cart.response

data class CartInfoResponseDto(
    val cartId: Long,
    val price: Int,
    val items: List<CartItemResponseDto> = mutableListOf(),
)
