package store.baribari.demo.repository.order

import org.springframework.data.jpa.repository.JpaRepository
import store.baribari.demo.model.order.Order

interface OrderRepository : JpaRepository<Order, Long>