package store.baribari.demo.repository.order

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.model.order.OrderItem

interface OrderItemRepository : JpaRepository<OrderItem, Long> {


    @Query(
        """
        SELECT oi 
        FROM OrderItem oi
        LEFT JOIN FETCH oi.order o
        LEFT JOIN FETCH o.user u
        WHERE oi.id = :orderItemId
        """
    )
    fun findByIdFetchOrderAndUser(orderItemId: Long): OrderItem?
}