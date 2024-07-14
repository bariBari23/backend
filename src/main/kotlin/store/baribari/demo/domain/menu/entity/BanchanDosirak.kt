package store.baribari.demo.domain.menu.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class BanchanDosirak(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banchan_dosirak_id")
    var id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banchan_id")
    val banchan: Banchan,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dosirak_id")
    val dosirak: Dosirak,
)
