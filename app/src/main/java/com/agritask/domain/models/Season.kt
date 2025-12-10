package com.agritask.domain.models

import java.time.LocalDate

data class Season(
    val id: Long,
    val activeFrom: LocalDate,
    val activeUntil: LocalDate? = null,
    val crop: Crop
) {

    fun isActive(now: LocalDate = LocalDate.now()): Boolean {
        return (activeFrom <= now) && (activeUntil == null || activeUntil >= now)
    }
}