package store.baribari.demo.model

import store.baribari.demo.model.embed.Position
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

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
    var position: Position = Position(0.0, 0.0),

    var businessName: String,

    // 사업자 등록번호
    var businessNumber: String,

    // 주소
    var address: String,

    // 메인 이미지
    var mainImage: String = "",

    // 이미지 리스트
    @ElementCollection
    var storeImageList: List<String> = emptyList(),

    // 가게 소개
    var description: String = "",

    // 가게 위생 정보
    var clean: String = "",

    //    @ElementCollection
    //    var dayList: MutableList<Day> = mutableListOf(),
    var dayList: String = "",

    var offDay: String = "",

    var walkingDistance: String = "",

    @OneToMany(mappedBy = "store")
    val likeUserList: MutableList<LikeStore> = mutableListOf(),
) : BaseEntity() {
    fun likeStore(likeStore: LikeStore) {
        this.likeUserList.add(likeStore)
    }

    fun cancelLikeStore(likeStore: LikeStore) {
        this.likeUserList.remove(likeStore)
    }
}