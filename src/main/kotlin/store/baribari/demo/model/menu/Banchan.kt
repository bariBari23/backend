package store.baribari.demo.model.menu

import store.baribari.demo.model.BaseEntity
import store.baribari.demo.model.Store
import javax.persistence.*

@Entity
class Banchan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banchan_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    val owner: Store,

    var name: String,

    var price: Int,

    var gram: Int,

    // 메인 이미지
    var mainImage: String? = null,

    // 이미지 리스트
    @ElementCollection
    var banchanImageList: List<String> = emptyList(),

    var description: String? = null,

    // 원산지
) : BaseEntity() {
    // 버림 하겠습니다~
    val pricePerHundred: Int
        get() = price / gram * 100
}