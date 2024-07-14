package store.baribari.demo.domain.cart.dto.response

data class CartInfoResponseDto(
    val cartId: Long,
    val price: Int,
    val items: List<CartItemResponseDto> = mutableListOf(),
)
