package store.baribari.demo.model.embed

import javax.persistence.Embeddable

@Embeddable
class Position(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
)