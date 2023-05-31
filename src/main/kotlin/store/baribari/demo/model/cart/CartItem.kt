package store.baribari.demo.model.cart

import store.baribari.demo.model.BaseEntity
import store.baribari.demo.model.menu.Dosirak
import store.baribari.demo.model.order.Order
import store.baribari.demo.model.order.OrderItem
import javax.persistence.*

@Entity
class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dosirak_id")
    val dosirak: Dosirak,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    val cart: Cart,

    var count: Int,
) : BaseEntity() {
    companion object {
        fun createCartItem(
            dosirak: Dosirak,
            cart: Cart,
            count: Int
        ): CartItem {
            return CartItem(
                dosirak = dosirak,
                cart = cart,
                count = count
            )
        }
    }

    fun addCount(count: Int) {
        this.count += count
    }
}
