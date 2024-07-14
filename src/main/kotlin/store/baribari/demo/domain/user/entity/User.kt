package store.baribari.demo.domain.user.entity

import org.hibernate.annotations.GenericGenerator
import store.baribari.demo.common.entity.BaseEntity
import store.baribari.demo.common.entity.embed.Position
import store.baribari.demo.common.enums.Role
import store.baribari.demo.domain.auth.ProviderType
import store.baribari.demo.domain.cart.domain.Cart
import store.baribari.demo.domain.order.entity.Order
import store.baribari.demo.domain.store.entity.LikeStore
import store.baribari.demo.domain.store.entity.Store
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
class User(
    @Id
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    var id: UUID? = null,
    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(30)")
    var email: String, // username 과 동의어
    @Column(name = "nickname", columnDefinition = "VARCHAR(30)")
    var nickname: String, // username 추가
    @Column(name = "password", columnDefinition = "VARCHAR(100)")
    var password: String = "NO_PASSWORD",
    @Column(name = "phoneNumber", columnDefinition = "VARCHAR(16)") // 전화번호 추가
    var phoneNumber: String?,
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: Role = Role.ROLE_CUSTOMER,
    @Column(name = "provider_id")
    var providerId: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type")
    var providerType: ProviderType = ProviderType.LOCAL,
    // 위치
    @Embedded
    var position: Position = Position(0.0, 0.0),
    // 장바구니
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST], optional = false)
    var userCart: Cart,
    // 프로필 이미지
    var profileImageUrl: String? = null,
    // 상점 목록
    @OneToMany(mappedBy = "owner")
    val storeList: MutableList<Store> = mutableListOf(),
    @OneToMany(mappedBy = "user")
    val likeStoreList: MutableList<LikeStore> = mutableListOf(),
    @OneToMany(mappedBy = "user")
    val orderList: MutableList<Order> = mutableListOf(),
) : BaseEntity() {
    fun encodePassword(encodedPassword: String) {
        password = encodedPassword
    }
}
