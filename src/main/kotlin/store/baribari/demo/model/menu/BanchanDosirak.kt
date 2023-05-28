package store.baribari.demo.model.menu

import javax.persistence.*

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
) {
}