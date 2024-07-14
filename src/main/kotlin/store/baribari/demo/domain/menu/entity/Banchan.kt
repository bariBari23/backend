package store.baribari.demo.domain.menu.entity

import store.baribari.demo.common.entity.BaseEntity
import store.baribari.demo.domain.store.entity.Store
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Banchan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banchan_id")
    var id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    val store: Store,
    var name: String,
    var gram: Int,
    // 메인 이미지
    var mainImage: String = "",
    // 이미지 리스트
    @ElementCollection
    var banchanImageList: List<String> = emptyList(),
    var description: String = "",
) : BaseEntity()
