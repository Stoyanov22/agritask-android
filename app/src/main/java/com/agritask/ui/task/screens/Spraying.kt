package com.agritask.ui.task.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.agritask.ui.Plot
import com.agritask.ui.SprayingReport

@Composable
fun SprayingScreen(
    viewModel: AppViewModel,
    currentPlot: Plot,
    navController: NavController,
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val areaUnits = viewModel.areaUnits


    var selectedUnit by remember { mutableStateOf(areaUnits.first()) }
    var isUnitMenuExpanded by remember { mutableStateOf(false) }
    val grower by viewModel.selectedGrower
    val group by viewModel.selectedPlotGroup

    var selectedAppMethod by remember { mutableStateOf("") }
    var selectedTargetList by remember { mutableStateOf("") }
    var targetText by remember { mutableStateOf("") }
    var appliedArea by remember { mutableStateOf("") }

    val appMethods = viewModel.applicationMethods
    val targetListOptions = viewModel.targetLists

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        val color1 = Color(0xFFE0F7FA)
        val color2 = Color(0xFFB2EBF2)

        InfoHeader(label = "Grower", value = grower?.name ?: "N/A", backgroundColor = color1)
        InfoHeader(label = "Plot Group", value = group?.name ?: "N/A", backgroundColor = color2)
        InfoHeader(label = "Plot", value = currentPlot.name, backgroundColor = color1)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Spraying",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            SimpleDropdown(
                label = "Application method",
                options = appMethods,
                selectedOption = selectedAppMethod,
                onOptionSelected = { selectedAppMethod = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SimpleDropdown(
                label = "Target list",
                options = targetListOptions,
                selectedOption = selectedTargetList,
                onOptionSelected = { selectedTargetList = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = targetText,
                onValueChange = { targetText = it },
                label = { Text("Target") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = appliedArea,
                onValueChange = {
                    if (it.all { char -> char.isDigit() || char == '.' }) {
                        appliedArea = it
                    }
                },
                label = { Text("Applied area") },
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
                            Text(
                                text = selectedUnit,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                                contentDescription = "Select unit",
                                modifier = Modifier.size(20.dp)
                            )
                        }


                        DropdownMenu(
                            expanded = isUnitMenuExpanded,
                            onDismissRequest = { isUnitMenuExpanded = false }
                        ) {
                            areaUnits.forEach { unit ->
                                DropdownMenuItem(
                                    text = { Text(text = unit) },
                                    onClick = {
                                        selectedUnit = unit
                                        isUnitMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = {
                    if (selectedAppMethod.isEmpty() || appliedArea.isEmpty()) {
                        android.widget.Toast.makeText(context, "Please fill all fields", android.widget.Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val report = SprayingReport(
                        plotId = currentPlot.id,
                        plotName = currentPlot.name,
                        applicationMethod = selectedAppMethod,
                        targetList = selectedTargetList,
                        target = targetText,
                        appliedArea = appliedArea.toDoubleOrNull() ?: 0.0,
                        unit = selectedUnit
                    )


                    viewModel.saveSprayingReport(report)

                    android.widget.Toast.makeText(context, "Report Saved Successfully!", android.widget.Toast.LENGTH_SHORT).show()

                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("SAVE REPORT")
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


@Composable
fun InfoHeader(label: String, value: String, backgroundColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Text(
            text = value,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDropdown(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}