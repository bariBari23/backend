package store.baribari.demo.model.cart

import store.baribari.demo.model.BaseEntity
import javax.persistence.*

@Entity
class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    var id: Long? = null,

    @OneToMany(mappedBy = "cart")
    val cartItemList: MutableList<CartItem> = mutableListOf(),
) : BaseEntity()