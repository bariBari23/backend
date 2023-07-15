package store.baribari.demo.model.order

import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.common.enums.PayMethod
import store.baribari.demo.common.exception.ConditionConflictException
import store.baribari.demo.model.BaseEntity
import store.baribari.demo.model.User
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var user: User? = null, //주문 회원

    var orderDemand: String = "", //주문 요청사항

    //@Pattern(regexp="(^$|[0-9]{10})")
    var orderPhoneNumber: String = "", // 주문자 전화번호

    var estimatedPickUpTime: String = "", // 픽업 시간

    var payMethod: PayMethod,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST])
    val orderItemList: MutableList<OrderItem> = mutableListOf(),

    ) : BaseEntity() {

    val status: OrderStatus //주문 상태 [ORDER, CANCEL]
        get() : OrderStatus {
            return when {
                orderItemList.isEmpty() -> OrderStatus.CANCELED

                orderItemList.all { it.status == OrderStatus.CANCELED } -> OrderStatus.CANCELED

                orderItemList.filterNot { it.status == OrderStatus.CANCELED }
                    .all { it.status == OrderStatus.READY } -> OrderStatus.READY
                // 모두 pickup이면 pickup으로
                orderItemList.filterNot { it.status == OrderStatus.CANCELED }
                    .all { it.status == OrderStatus.PICKED_UP } -> OrderStatus.PICKED_UP
                //pickup 상태 completed 섞여있다면 completed로
                orderItemList.filterNot { it.status == OrderStatus.CANCELED }
                    .all { it.status == OrderStatus.PICKED_UP || it.status == OrderStatus.COMPLETED } -> OrderStatus.COMPLETED


                else -> OrderStatus.ORDERED
            }
        }

    val price: Int
        get() = orderItemList
            .filterNot { it.status == OrderStatus.CANCELED }
            .sumOf { it.price }


    //==연관관계 메서드==//
    fun setCustomer(user: User) {
        this.user = user
        user.orderList.add(this)
    }

    fun setOrderItemAndStock(orderItemList: List<OrderItem>) {
        this.orderItemList.addAll(orderItemList)
        orderItemList.forEach { it.dosirak.removeStock(it.count) }
    }

    //==비즈니스 로직==//
    fun cancel() {
        when (this.status) {
            OrderStatus.COMPLETED, OrderStatus.CANCELED, OrderStatus.PICKED_UP -> {
                throw ConditionConflictException(
                    ErrorCode.CANCELED_IMPOSSIBLE,
                    "이미 준비완료, 픽업완료, 취소된 항목은 취소가 불가능합니다."
                )
            }

            else -> {
                orderItemList.filterNot { it.status == OrderStatus.CANCELED }.forEach { it.cancel() }
            }
        }
    }


    // 테스트용 함수
    // TODO: 실제 서비스 구현시에는 사용하지 않는다.
    fun forcePickUp() {
        // 주문 상태가 cancel이 아니면 orderItem을 PICKUP으로 바꾼다.
        if (this.status != OrderStatus.CANCELED) {
            orderItemList.filterNot { it.status == OrderStatus.CANCELED }.forEach { it.status = OrderStatus.PICKED_UP }
        }
    }

    fun ordered() {
        orderItemList.filterNot { it.status == OrderStatus.CANCELED }.forEach { it.ordered() }
    }

    fun complete() {
        orderItemList.filterNot { it.status == OrderStatus.CANCELED }.forEach { it.complete() }
    }

}