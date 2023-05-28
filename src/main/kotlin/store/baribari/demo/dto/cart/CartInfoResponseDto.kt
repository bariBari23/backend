package store.baribari.demo.dto.cart

data class CartInfoResponseDto(
    val cartId: Long,
    val items: List<CartItemResponseDto> = mutableListOf(),
)
