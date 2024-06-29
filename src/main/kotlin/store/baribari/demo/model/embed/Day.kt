package store.baribari.demo.model.embed

import java.time.DayOfWeek
import java.time.LocalTime
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class Day(
    @Enumerated(EnumType.STRING)
    val day: DayOfWeek,
    val openTime: LocalTime,
    val closeTime: LocalTime,
)
