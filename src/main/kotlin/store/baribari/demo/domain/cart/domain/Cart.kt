package store.baribari.demo.domain.cart.domain

import store.baribari.demo.common.entity.BaseEntity
import java.util.UUID
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
    // 임시로 user_id를 배치해 놓았지만 추후에는 제거하는 방식으로
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    var userId: UUID? = null,
    @OneToMany(mappedBy = "cart")
    val cartItemList: MutableList<CartItem> = mutableListOf(),
) : BaseEntity() {
    private val price: Int
        get() = cartItemList.sumOf { it.dosirak.price * it.count }
}
