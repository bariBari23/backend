package store.baribari.demo.model

import store.baribari.demo.model.embed.Day
import javax.persistence.*

@Entity
class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val owner: User,

    var name: String,

    var phoneNumber: String,

    // 지도 좌표

    var businessName: String,

    // 사업자 등록번호
    var businessNumber: String,

    // 주소
    var address: String,

    // 메인 이미지
    var mainImage: String? = null,

    // 이미지 리스트
    @ElementCollection
    var storeImageList: List<String> = emptyList(),

    var soldOut: Boolean = false,

    // 가게 소개
    var description: String?,

    // 가게 위생 정보
    var clean: String? = null,

    @ElementCollection
    var dayList: List<Day> = emptyList(),
) : BaseEntity()