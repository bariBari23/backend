package store.baribari.demo.service

import org.springframework.data.domain.Pageable
import store.baribari.demo.dto.MyOrderItemResponseDto

interface OrderItemService {
    fun findMyOrderItem(
        userEmail: String,
        pageable: Pageable
    ): MyOrderItemResponseDto
}
