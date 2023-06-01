package store.baribari.demo.dto.order.response

import org.springframework.data.domain.Pageable

data class FindAllOrderResponseDto(
    val orderList: List<FindOneOrderResponseDto>,
    val pageable: Pageable,
)