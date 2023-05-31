package store.baribari.demo.common.enums

enum class OrderStatus(
    val message: String
) {
    READY("아직 결제가 이루어지지 않은 주문입니다.") ,CANCELED("취소가 된 주문입니다."), ORDERED("주문이 들어갔습니다."), COMPLETED("준비가 완료 되었습니다. 픽업해주시면 됩나다."), PICKED_UP("픽업이 완료 되었습니다. 맛있게 드세요.")
}
