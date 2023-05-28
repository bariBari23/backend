package store.baribari.demo.model.order

import store.baribari.demo.model.BaseEntity
import store.baribari.demo.model.menu.Dosirak
import javax.persistence.*

@Entity
class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dosirak_id")
    val dosirak: Dosirak,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    val order: Order,

    var count: Int,
) : BaseEntity() {

    fun cancel() {
        dosirak.addStock(count)
    }

    val price: Int
        get() = dosirak.price * count

    companion object {
        fun createOrderItem(
            dosirak: Dosirak,
            order: Order,
            count: Int
        ): OrderItem {
            // TODO: 재고 관리 주문이 들어가면 단일서버는 상관 없지만 다중 서버면 처리를 반드시 해야한다. 나중에 구현
            dosirak.removeStock(count)
            return OrderItem(
                dosirak = dosirak,
                order = order,
                count = count,
            )
        }
    }
}