package store.baribari.demo.repository.order

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.model.order.Order

interface OrderRepository : JpaRepository<Order, Long> {

    @Query(
        """
        SELECT o
        FROM Order o
        LEFT JOIN FETCH o.user user
        LEFT JOIN FETCH o.orderItemList orderItem
        WHERE o.id = :orderId
        """
    )
    fun findByIdFetchUserAndOrderItem(orderId: Long): Order?
}