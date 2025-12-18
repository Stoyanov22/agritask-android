package com.agritask.domain.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.model.Polygon
import java.time.LocalDate

class Location(
    val id: Long,
    val code: String,
    val activeFrom: LocalDate,
    val activeUntil: LocalDate? = null,
    val season: Season,
    val area: Double,
    val type: LocationType,
    val ownerLocation: Location? = null,
    val locationNodes: List<Location> = emptyList(),
    val polygon: Polygon? = null,
    val latitude: Double,
    val longitude: Double,
    val additionalProperties: String? = null
) {

    constructor(
        code: String,
        activeFrom: LocalDate,
        season: Season,
        area: Double,
        type: LocationType,
        latitude: Double,
        longitude: Double
    ) : this(
        id = 0L,
        code = code,
        activeFrom = activeFrom,
        activeUntil = null,
        season = season,
        area = area,
        type = type,
        ownerLocation = null,
        locationNodes = emptyList(),
        polygon = null,
        latitude = latitude,
        longitude = longitude,
        additionalProperties = null
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun isActive(date: LocalDate = LocalDate.now()): Boolean {
        return !date.isBefore(activeFrom) &&
                (activeUntil == null || !date.isAfter(activeUntil))
    }

    fun hasPolygon(): Boolean = polygon != null

    fun isRootLocation(): Boolean = ownerLocation == null

    fun hasChildren(): Boolean = locationNodes.isNotEmpty()

    fun isOfType(vararg types: LocationType): Boolean =
        types.contains(type)

    fun isValidCoordinates(): Boolean =
        latitude in -90.0..90.0 && longitude in -180.0..180.0

    fun addChild(child: Location): Location =
        copyWith(locationNodes = locationNodes + child)

    fun copyWith(
        activeUntil: LocalDate? = this.activeUntil,
        polygon: Polygon? = this.polygon,
        locationNodes: List<Location> = this.locationNodes,
        additionalProperties: String? = this.additionalProperties
    ): Location =
        Location(
            id = id,
            code = code,
            activeFrom = activeFrom,
            activeUntil = activeUntil,
            season = season,
            area = area,
            type = type,
            ownerLocation = ownerLocation,
            locationNodes = locationNodes,
            polygon = polygon,
            latitude = latitude,
            longitude = longitude,
            additionalProperties = additionalProperties
        )
}