package store.baribari.demo.dto.order.response

import org.springframework.data.domain.Pageable

data class MyOrderResponseDto(
    val orderList: List<FindOneOrderResponseDto>,
    val pageable: Pageable,
)
