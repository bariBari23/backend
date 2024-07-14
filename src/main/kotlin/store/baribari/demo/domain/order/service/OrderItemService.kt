package store.baribari.demo.domain.order.service

import org.springframework.data.domain.Pageable
import store.baribari.demo.domain.order.dto.MyOrderItemResponseDto

interface OrderItemService {
    fun findMyOrderItem(
        userEmail: String,
        pageable: Pageable,
    ): MyOrderItemResponseDto
}
