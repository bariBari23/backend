package store.baribari.demo.domain.order.dto.request

data class CancelOrderItemResponseDto(
    val orderItemId: Long,
    val price: Int,
)
