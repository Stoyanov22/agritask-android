package com.agritask.domain.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class Season(
    val id: Long,
    val plotId: Long,
    val activeFrom: LocalDate,
    val activeUntil: LocalDate? = null,
    val crop: Crop,
    val additionalProperties: String? = null
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun isActive(now: LocalDate = LocalDate.now()): Boolean {
        return (activeFrom <= now) && (activeUntil == null || activeUntil >= now)
    }
}