package store.baribari.demo.dto.order.response

data class FindAllOrderResponseDto(
    val orderCount: Long,
    val orderList: List<FindOneOrderResponseDto>,
)