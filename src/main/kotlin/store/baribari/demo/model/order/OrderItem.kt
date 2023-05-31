package store.baribari.demo.model.order

import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.common.enums.PayMethod
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
    var order: Order,

    @Enumerated(EnumType.STRING)
    var status: OrderStatus, //주문상태 [ORDERED, CANCEL, COMPLETE, PICKUP]

    var count: Int,
) : BaseEntity() {

    fun cancel() {
        this.status = OrderStatus.CANCELED
        dosirak.addStock(count)
    }

    fun review() {
        if (this.isReviewed || this.status != OrderStatus.PICKED_UP)
            throw IllegalStateException("리뷰를 작성할 수 없습니다.")
        this.isReviewed = true
    }

    val price: Int
        get() = dosirak.price * count

    private var isReviewed: Boolean = false

    companion object {
        fun createOrderItem(
            dosirak: Dosirak,
            order: Order,
            count: Int,
            payMethod: PayMethod,
        ): OrderItem {
            // TODO: 재고 관리 주문이 들어가면 단일서버는 상관 없지만 다중 서버면 처리를 반드시 해야한다. 나중에 구현
            return OrderItem(
                dosirak = dosirak,
                order = order,
                count = count,
                status = if (payMethod == PayMethod.CASH) OrderStatus.READY else OrderStatus.ORDERED
            )
        }
    }
}