package store.baribari.demo.common.entity.embed

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Address {
    // 주소 정보
    val city: String = "" // 도시
    val district: String = "" // 구

    @Column(name = "address_detail")
    val detail: String = "" // 상세 주소
    val zipCode: String = "" // 우편번호
}
