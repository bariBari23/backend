package store.baribari.demo.auth

class GoogleOAuth2UserInfo(
    attributes: MutableMap<String, Any>,
) : OAuth2UserInfo {
    override val id: String = attributes["sub"] as String
    override val name: String = attributes["name"] as String
    override val email: String = attributes["email"] as String
    override val imageUrl: String = attributes["picture"] as String
    override val phoneNumber: String? = null
}