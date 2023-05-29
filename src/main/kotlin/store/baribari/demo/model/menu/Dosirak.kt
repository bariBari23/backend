package store.baribari.demo.model.menu

import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.exception.ConditionConflictException
import store.baribari.demo.model.BaseEntity
import store.baribari.demo.model.Store
import javax.persistence.*

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
    var mainImage: String? = null,

    // 이미지 리스트
    @ElementCollection
    var dosirakImageList: List<String> = emptyList(),

    var description: String? = null,
) : BaseEntity() {

    private var stock: Int = 0

    val price: Int
        get() = getTotalPrice()

    private fun getTotalPrice(): Int {
        var totalPrice = 0
        // 쿼리 한번 따져보자 가격 계산은 자주 일어나는데 할 때마다 쿼리를 소환하는게 말이 안된다고 생각함
        // 심지어 한번 설정된 가격은 잘 바뀌지 아니한다.
        // 차라리 배열이 고쳐질 때 set으로 가격을 최신화하고 이외에는 get으로 값 자체를 부르는 것이 좋다고 생각
        for (dosirakBanchan in dosirakBanchanList) {
            totalPrice += dosirakBanchan.banchan.price
        }
        return totalPrice
    }

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