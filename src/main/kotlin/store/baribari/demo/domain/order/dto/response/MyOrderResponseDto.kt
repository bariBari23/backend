package store.baribari.demo.domain.order.dto.response

import org.springframework.data.domain.Pageable

data class MyOrderResponseDto(
    val orderList: List<FindOneOrderResponseDto>,
    val pageable: Pageable,
)
