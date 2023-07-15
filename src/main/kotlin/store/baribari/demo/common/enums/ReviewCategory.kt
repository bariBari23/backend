package store.baribari.demo.common.enums

enum class ReviewCategory(
    val comment: String,
) {
    smallAmount("양이 적어요"),
    averageAmount("충분해요"),
    largeAmount("너무 많아요"),
    badTaste("별로예요"),
    plainTaste("보통이에요"),
    goddTaste("맛있어요"),
    badStatus("허술해요"),
    plainStatus("보통이에요"),
    goodStatus("깔끔해요"),
}
