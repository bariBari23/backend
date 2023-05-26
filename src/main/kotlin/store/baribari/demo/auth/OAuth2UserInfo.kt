package store.baribari.demo.auth

abstract class OAuth2UserInfo(
    val attributes: MutableMap<String, Any>,
) {
    abstract fun getId(): String

    abstract fun getName(): String

    abstract fun getEmail(): String

    abstract fun getImageUrl(): String
}