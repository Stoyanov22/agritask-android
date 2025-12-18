package com.agritask.ui.task.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.agritask.ui.AppViewModel
import com.agritask.ui.FertilizationReport
import com.agritask.ui.Plot
import com.agritask.ui.ScoutingReport
import java.time.LocalDateTime
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Fertilization(
    navController: NavController,
    viewModel: AppViewModel,
    currentPlot: Plot
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val grower by viewModel.selectedGrower
    val group by viewModel.selectedPlotGroup

    // State
    var selectedFertilizer by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    // Unit Logic
    val units = viewModel.fertilizerUnits
    var selectedUnit by remember { mutableStateOf(units.firstOrNull() ?: "kg") }
    var isUnitMenuExpanded by remember { mutableStateOf(false) }

    // Data
    val fertilizers = viewModel.fertilizers

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Headers
        InfoHeader(label = "Grower", value = grower?.name ?: "-", backgroundColor = Color(0xFFE0F7FA))
        InfoHeader(label = "Plot Group", value = group?.name ?: "-", backgroundColor = Color(0xFFB2EBF2))
        InfoHeader(label = "Plot", value = currentPlot.name, backgroundColor = Color(0xFFE0F7FA))

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Fertilization Report",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF795548), // Кафяво за почва/торене
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {


            SimpleDropdown(
                label = "Fertilizer Product",
                options = fertilizers,
                selectedOption = selectedFertilizer,
                onOptionSelected = { selectedFertilizer = it }
            )

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value = quantity,
                onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) quantity = it },
                label = { Text("Quantity per ha") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                suffix = {
                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { isUnitMenuExpanded = true }
                                .padding(4.dp)
                        ) {
                            Text(selectedUnit, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null, modifier = Modifier.size(20.dp))
                        }
                        DropdownMenu(
                            expanded = isUnitMenuExpanded,
                            onDismissRequest = { isUnitMenuExpanded = false }
                        ) {
                            units.forEach { unit ->
                                DropdownMenuItem(text = { Text(unit) }, onClick = { selectedUnit = unit; isUnitMenuExpanded = false })
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {

                    if (selectedFertilizer.isEmpty() || quantity.isEmpty()) {
                        Toast.makeText(context, "Please select fertilizer and quantity", Toast.LENGTH_SHORT).show()
                        return@Button
                    }


                    val report = FertilizationReport(
                        plotId = currentPlot.id,
                        plotName = currentPlot.name,
                        fertilizer = selectedFertilizer,
                        quantity = quantity.toDoubleOrNull() ?: 0.0,
                        unit = selectedUnit
                    )

                    viewModel.saveFertilizationReport(report)

                    Toast.makeText(context, "Fertilization Saved!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                          },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548))
            ) {
                Text("SAVE REPORT")
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}