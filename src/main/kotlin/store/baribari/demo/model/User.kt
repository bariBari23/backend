package store.baribari.demo.model


import org.hibernate.annotations.GenericGenerator
import store.baribari.demo.auth.ProviderType
import store.baribari.demo.common.enums.Role
import java.util.*
import javax.persistence.*

@Entity
class User(
    @Id
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    var id: UUID? = null,

    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(30)")
    var email: String, // username 과 동의어

    @Column(name = "password", columnDefinition = "VARCHAR(100)")
    var password: String = "NO_PASSWORD",

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: Role = Role.ROLE_CUSTOMER,

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type")
    var providerType: ProviderType,

    @Column(name = "provider_id")
    var providerId: String? = null,

    // 프로필 이미지
    var profileImageUrl: String? = null,

    ) : BaseEntity() {
    fun encodePassword(encodedPassword: String) {
        password = encodedPassword
    }
}
