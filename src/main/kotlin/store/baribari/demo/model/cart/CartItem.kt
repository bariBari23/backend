package store.baribari.demo.model.cart

import store.baribari.demo.model.BaseEntity
import store.baribari.demo.model.menu.Dosirak
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

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
            count: Int,
        ): CartItem {
            return CartItem(
                dosirak = dosirak,
                cart = cart,
                count = count,
            )
        }
    }

    fun addCount(count: Int) {
        this.count += count
    }
}
