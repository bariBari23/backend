package store.baribari.demo.dto.order.request

data class CancelOrderItemResponseDto(
    val orderItemId: Long,
    val price: Int,
)
