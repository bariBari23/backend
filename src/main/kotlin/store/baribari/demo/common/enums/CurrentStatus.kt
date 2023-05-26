package store.baribari.demo.common.enums

enum class CurrentStatus(
    val day: Int?,
    val message: String,
) {
    NORMAL(0, "정상 회원"),
    SUSPENDED_ONE(1, "1일 정지"),
    SUSPENDED_THREE(3, "3일 정지"),
    DELETED(null, "삭제됨")
}
