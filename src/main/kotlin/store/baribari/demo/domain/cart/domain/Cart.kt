package store.baribari.demo.domain.cart.domain

import store.baribari.demo.common.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    var id: Long? = null,
    @OneToMany(mappedBy = "cart")
    val cartItemList: MutableList<CartItem> = mutableListOf(),
) : BaseEntity() {
    private val price: Int
        get() = cartItemList.sumOf { it.dosirak.price * it.count }
}
