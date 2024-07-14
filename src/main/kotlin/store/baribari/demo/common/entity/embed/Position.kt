package store.baribari.demo.common.entity.embed

import javax.persistence.Embeddable

@Embeddable
class Position(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
)
