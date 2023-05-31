package store.baribari.demo.dto

data class FindAllOrderResponseDto(
    val orderCount: Long,
    val orderList: List<FindOneOrderResponseDto>,
)