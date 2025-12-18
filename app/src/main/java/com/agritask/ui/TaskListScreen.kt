package com.agritask.ui

import Task
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.agritask.ui.task.screens.InfoHeader

@Composable
fun TaskListScreen(
    navController: NavController,
    viewModel: AppViewModel
) {
    val grower = viewModel.selectedGrower.value
    val group = viewModel.selectedPlotGroup.value
    val plot = viewModel.selectedPlot.value
    val tasks by viewModel.filteredTasks

    Column(modifier = Modifier.fillMaxSize()) {

        InfoHeader(label = "Grower", value = grower?.name ?: "-", backgroundColor = Color(0xFFE0F7FA))
        InfoHeader(label = "Plot Group", value = group?.name ?: "-", backgroundColor = Color(0xFFB2EBF2))
        InfoHeader(label = "Plot", value = plot?.name ?: "-", backgroundColor = Color(0xFFE0F7FA))

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Available Tasks",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tasks) { task ->
                TaskItem(task = task, onClick = {
                    when (task.type) {
                        TaskType.SPRAYING -> navController.navigate("spraying")
                        TaskType.SCOUTING -> navController.navigate("scouting")
                        TaskType.FERTILIZATION -> navController.navigate("fertilization")
                    }
                })
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = task.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Type: ${task.type}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Text(text = task.date, style = MaterialTheme.typography.bodyMedium)
        }
    }
}