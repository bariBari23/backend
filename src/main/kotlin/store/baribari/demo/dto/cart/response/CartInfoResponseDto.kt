package store.baribari.demo.dto.cart.response

data class CartInfoResponseDto(
    val cartId: Long,
    val items: List<CartItemResponseDto> = mutableListOf(),
)
