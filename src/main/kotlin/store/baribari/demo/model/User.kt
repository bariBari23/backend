package store.baribari.demo.model


import org.hibernate.annotations.GenericGenerator
import store.baribari.demo.auth.ProviderType
import store.baribari.demo.common.enums.Role
import store.baribari.demo.model.cart.Cart
import store.baribari.demo.model.embed.Position
import store.baribari.demo.model.order.Order
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

    @Column(name = "provider_id")
    var providerId: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type")
    var providerType: ProviderType = ProviderType.LOCAL,

    //위치
    @Embedded
    var position: Position = Position(0.0, 0.0),

// 장바구니
    var userCart: Cart? = null,

// 프로필 이미지
    var profileImageUrl: String? = null,

// 상점 목록
    @OneToMany(mappedBy = "owner")
    val storeList: MutableList<Store> = mutableListOf(),

    @OneToMany(mappedBy = "user")
    val orderList: MutableList<Order> = mutableListOf(),
) : BaseEntity() {
    fun encodePassword(encodedPassword: String) {
        password = encodedPassword
    }
}
