package com.agritask.ui

import android.os.Build
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListScreen(
    navController: NavController,
    viewModel: AppViewModel
) {
    val grower = viewModel.selectedGrower.value
    val group = viewModel.selectedPlotGroup.value
    val plot = viewModel.selectedPlot.value
    val tasks = viewModel.getTasksForSelectedPlot()
    val activeSeason = plot?.let { viewModel.getActiveSeasonForPlot(it.id) }
    val cropName = activeSeason?.crop?.name ?: "No Active Crop"

    Column(modifier = Modifier.fillMaxSize()) {

        InfoHeader(label = "Grower", value = grower?.name ?: "-", backgroundColor = Color(0xFFE0F7FA))
        InfoHeader(label = "Plot Group", value = group?.name ?: "-", backgroundColor = Color(0xFFB2EBF2))
        InfoHeader(label = "Plot", value = plot?.name ?: "-", backgroundColor = Color(0xFFE0F7FA))
        InfoHeader(label = "Active Crop", value = cropName, backgroundColor = Color(0xFFE8F5E9))

        Spacer(modifier = Modifier.height(16.dp))

        if (tasks.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No tasks available. Plot might be inactive.")
            }
        } else {
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
                            TaskType.METEOROLOGY -> navController.navigate("meteorology")
                            TaskType.DRILLING -> navController.navigate("drilling")
                            TaskType.HARVEST -> navController.navigate("harvest")
                        }
                    })
                }
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
        }
    }
}