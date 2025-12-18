enum class TaskType {
    SPRAYING,
    SCOUTING,
    FERTILIZATION
}

data class Task(
    val id: Long,
    val title: String,
    val type: TaskType,
    val plotIds: List<Long>,
    val date: String
)