package store.baribari.demo.domain.cart.repository

import org.springframework.data.jpa.repository.JpaRepository
import store.baribari.demo.domain.cart.domain.CartItem

interface CartItemRepository : JpaRepository<CartItem, Long>
