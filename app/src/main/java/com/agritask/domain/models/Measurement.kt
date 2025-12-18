package com.agritask.domain.models

//import com.google.android.gms.fido.fido2.api.common.Attachment
import java.sql.Timestamp
import java.time.LocalDate

import java.time.Instant

data class Measurement(
    val id: Long = 0L,
    val timestamp: Instant = Instant.now(),
    val locationId: Long,
    val measurementCode: String,
    val taskCode: String,
    val notes: String? = null,
    val data: String,
    val userId: Long,
    val latitude: Double,
    val longitude: Double,
    val status: MeasurementStatus,
    val additionalProperties: String? = null
    //TODO: add attachments.
) {

    fun isValidCoordinates(): Boolean =
        latitude in -90.0..90.0 && longitude in -180.0..180.0
}