package store.baribari.demo.common.enums

enum class PayMethod(
    val displayName: String,
) {
    CASH("현금"),
    CARD("카드"),
    KAKAOPAY("카카오페이"),
    BANK_TRANSFER("계좌이체"),
}
