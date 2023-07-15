package store.baribari.demo.model.menu

import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.exception.ConditionConflictException
import store.baribari.demo.model.BaseEntity
import store.baribari.demo.model.Store
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
class Dosirak(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dosirak_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    val store: Store,

    var name: String,

    // 반찬 리스트
    // 수정하는 케이스는 지금 고려하지 말자
    @OneToMany(mappedBy = "dosirak")
    var dosirakBanchanList: MutableList<BanchanDosirak> = mutableListOf(),

    // 메인 이미지
    var mainImage: String = "",

    // 이미지 리스트
    @ElementCollection
    var dosirakImageList: List<String> = emptyList(),

    var description: String = "",

    var fromWhere: String = "",

    var mealTimes: Int,

    val price: Int,

    var stock: Int = 0,
) : BaseEntity() {

    val gram: Int
        get() = dosirakBanchanList.sumOf { it.banchan.gram }

    private val soldOut: Boolean
        get() = stock <= 0

    // 재고를 매일 변경
    fun changeStock(count: Int) {
        stock = count
    }

    fun addStock(count: Int) {
        stock += count
    }

    fun removeStock(count: Int) {
        val restStock = stock - count

        if (restStock < 0)
            throw ConditionConflictException(ErrorCode.NOT_ENOUGH_STOCK, "need more stock")

        stock = restStock
    }
}