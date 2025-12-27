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

data class MeteorologyReport(
    val plotId: Long,
    val plotName: String,
    val precipitation: Double, // mm
    val temperature: Double,   // C
    val hail: Double,          // mm
    val frost: Double,         // %
    val timestamp: Long = System.currentTimeMillis()
) : TaskReport

data class DrillingReport(
    val plotId: Long,
    val plotName: String,
    val startDate: String,
    val endDate: String,
    val area: Double,
    val machine: String,
    val implements: List<String>,
    val timestamp: Long = System.currentTimeMillis()
) : TaskReport

data class MachineUsageData(
    val machineName: String,
    val hoursUsed: Double
)

data class HarvestReport(
    val plotId: Long,
    val plotName: String,
    val startDate: String,
    val endDate: String,
    val area: Double,
    val totalYield: Double,
    val machines: List<MachineUsageData>,
    val timestamp: Long = System.currentTimeMillis()
) : TaskReport