package store.baribari.demo.auth

interface OAuth2UserInfo {
    val id: String
    val name: String
    val email: String
    val imageUrl: String
    val phoneNumber: String?
}
