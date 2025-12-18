package com.agritask.ui

sealed interface TaskReport

data class SprayingReport(
    val plotId: Long,
    val plotName: String,
    val applicationMethod: String,
    val targetList: String,
    val target: String,
    val appliedArea: Double,
    val unit: String,
    val timestamp: Long = System.currentTimeMillis()
) : TaskReport

data class ScoutingReport(
    val plotId: Long,
    val plotName: String,
    val observationType: String,
    val severity: String,
    val notes: String,
    val timestamp: Long = System.currentTimeMillis()
) : TaskReport

data class FertilizationReport(
    val plotId: Long,
    val plotName: String,
    val fertilizer: String,
    val quantity: Double,
    val unit: String,
    val timestamp: Long = System.currentTimeMillis()
) : TaskReport