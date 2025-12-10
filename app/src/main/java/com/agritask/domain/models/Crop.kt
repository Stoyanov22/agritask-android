package com.agritask.domain.models

import java.time.LocalDate

data class Crop(
    val id: Long,
    val activeFrom: LocalDate,
    val activeUntil: LocalDate? = null,
) {

    fun isActive(now: LocalDate = LocalDate.now()): Boolean {
        return (activeFrom <= now) && (activeUntil == null || activeUntil >= now)
    }
}