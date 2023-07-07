package store.baribari.demo.common.enums

enum class ReviewCategory(
    val comment: String,
) {
    small_amount("양이 적어요"),
    average_amount("충분해요"),
    large_amount("너무 많아요"),
    bad_taste("별로예요"),
    plain_taste("보통이에요"),
    good_taste("맛있어요"),
    bad_status("허술해요"),
    plain_status("보통이에요"),
    good_status("깔끔해요"),
}
