package store.baribari.demo.model.order

import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.model.BaseEntity
import store.baribari.demo.model.User
import javax.persistence.*

@Entity
class Order(
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var user: User, //주문 회원

    @OneToMany(mappedBy = "order")
    private val orderItems: MutableList<OrderItem> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    private val status: OrderStatus, //주문상태 [ORDER, CANCEL, COMPLETE, PICKUP]
) : BaseEntity() {
    //==연관관계 메서드==//
//    fun setUser(member: User) {
//        this.user = member
//        // member.order.add(this)
//    }
//
//    fun addOrderItem(orderItem: OrderItem) {
//        orderItems.add(orderItem)
//        // orderItem.order(this)
//    }
//
//    //==비즈니스 로직==//
//    /** 주문 취소  */
//    fun cancel() {
//        // check(delivery.getStatus() !== OrderStatus.COMP) { "이미 배송완료된 상품은 취소가 불가능합니다." }
//        // TODO: 픽업 완료가 되었으면 취소가 불가능함
//
//        // this.setStatus(OrderStatus.CANCEL)
//        for (orderItem in orderItems) {
//            orderItem.cancel()
//        }
//    }
//
//    //==조회 로직==//
//    val totalPrice: Int
//        /** 전체 주문 가격 조회  */
//        get() {
//            var totalPrice = 0
//            for (orderItem in orderItems) {
//                totalPrice += orderItem.price
//            }
//            return totalPrice
//        }
//
//    companion object {
//        //==생성 메서드==//
//
//    }
}