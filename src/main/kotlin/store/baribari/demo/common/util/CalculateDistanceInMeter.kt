package store.baribari.demo.common.util

import store.baribari.demo.model.embed.Position
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


const val EARTH_RADIUS: Double = 6371.0

fun calculateDistanceInMeter(
    userPosition: Position,
    storePosition: Position
): Double {
    /**
     * 6371.0 is the radius of the Earth in kilometers.
     */

    val lat1 = userPosition.latitude;
    val lon1 = userPosition.longitude
    val lat2 = storePosition.latitude;
    val lon2 = storePosition.longitude

    val dLat = (lat2 - lat1).toRadians()
    val dLon = (lon2 - lon1).toRadians()

    val a = sin(dLat / 2).pow(2.0) +
            cos(lat1.toRadians()) * cos(lat2.toRadians()) *
            sin(dLon / 2).pow(2.0)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return EARTH_RADIUS * c * 1000
}

/**
 * Converts an angle in degrees to radians.
 */
fun Double.toRadians() = this * PI / 180