package com.agritask.ui.task.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.agritask.ui.AppViewModel
import com.agritask.ui.Plot
import com.agritask.ui.ScoutingReport

@Composable
fun Scouting(
    navController: NavController,
    viewModel: AppViewModel,
    currentPlot: Plot
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val grower by viewModel.selectedGrower
    val group by viewModel.selectedPlotGroup

    // Local State
    var selectedType by remember { mutableStateOf("") }
    var selectedSeverity by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    // Data
    val types = viewModel.observationTypes
    val severities = viewModel.severityLevels

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        InfoHeader(label = "Grower", value = grower?.name ?: "-", backgroundColor = Color(0xFFE0F7FA))
        InfoHeader(label = "Plot Group", value = group?.name ?: "-", backgroundColor = Color(0xFFB2EBF2))
        InfoHeader(label = "Plot", value = currentPlot.name, backgroundColor = Color(0xFFE0F7FA))

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Scouting Report",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFEF6C00), // Оранжево за Оглед
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            // 1. Observation Type
            SimpleDropdown(
                label = "Observation Type",
                options = types,
                selectedOption = selectedType,
                onOptionSelected = { selectedType = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Severity (Важно за агронома)
            SimpleDropdown(
                label = "Severity / Damage Level",
                options = severities,
                selectedOption = selectedSeverity,
                onOptionSelected = { selectedSeverity = it }
            )

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes / Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedButton(
                onClick = { /* TODO: Open Camera */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("ADD PHOTO")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val report = ScoutingReport(
                        plotId = currentPlot.id,
                        plotName = currentPlot.name,
                        observationType = selectedType,
                        severity = selectedSeverity,
                        notes = notes
                    )
                    viewModel.saveScoutingReport(report)
                    android.widget.Toast.makeText(context, "Scouting Report Saved!", android.widget.Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF6C00))
            ) {
                Text("SAVE REPORT")
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}