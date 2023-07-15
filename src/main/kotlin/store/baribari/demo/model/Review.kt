package store.baribari.demo.model

import store.baribari.demo.common.enums.ReviewCategory
import store.baribari.demo.model.order.OrderItem
import javax.persistence.*

@Entity
class Review(
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    val writer: User,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    val orderItem: OrderItem,

    val rating: Int,

    val content: String,

    @ElementCollection
    @Enumerated(EnumType.STRING)
    val tags: MutableList<ReviewCategory>,

    val mainImageUrl: String = "",

    @ElementCollection
    val photoList: MutableList<String> = mutableListOf(),
) : BaseEntity()