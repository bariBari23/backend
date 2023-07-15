package store.baribari.demo.dto

import org.springframework.data.domain.Page
import store.baribari.demo.model.order.OrderItem

data class MyOrderItemResponseDto(
    val orderItems: List<MyOrderItemElementDto>,
    val currentPage: Int,
    val totalPages: Int,
    val totalElements: Long,
    val numberOfElements: Int,
) {
    companion object {
        fun fromOrderItemPageable(
            orderItems: Page<OrderItem>
        ): MyOrderItemResponseDto {
            //orderitem 개별로 하나씩 만들어야

            return MyOrderItemResponseDto(
                orderItems = orderItems.content.map { MyOrderItemElementDto.fromOrderItem(it) },
                currentPage = orderItems.number,
                totalPages = orderItems.totalPages,
                totalElements = orderItems.totalElements,
                numberOfElements = orderItems.numberOfElements,
            )
        }
    }
}
