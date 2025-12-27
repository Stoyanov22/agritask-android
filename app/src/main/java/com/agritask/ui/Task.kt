package com.agritask.ui
enum class TaskType {
    SPRAYING,
    SCOUTING,
    FERTILIZATION,
    METEOROLOGY,
    DRILLING,
    HARVEST
}

data class Task(
    val id: Long,
    val title: String,
    val type: TaskType,
    val validForCropIds: List<Long>,
    val description: String = ""
)