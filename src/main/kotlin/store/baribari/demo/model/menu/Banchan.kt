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

    var gram: Int,

    // 메인 이미지
    var mainImage: String = "",

    // 이미지 리스트
    @ElementCollection
    var banchanImageList: List<String> = emptyList(),

    var description: String = "",
) : BaseEntity()