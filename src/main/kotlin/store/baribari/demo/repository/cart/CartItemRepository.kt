package store.baribari.demo.repository.cart

import org.springframework.data.jpa.repository.JpaRepository
import store.baribari.demo.model.cart.CartItem

interface CartItemRepository : JpaRepository<CartItem, Long>
