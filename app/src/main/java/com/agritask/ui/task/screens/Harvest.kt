package com.agritask.ui.task.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.agritask.ui.AppViewModel
import com.agritask.ui.HarvestReport
import com.agritask.ui.MachineUsageData
import com.agritask.ui.Plot
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Harvest(
    navController: NavController,
    viewModel: AppViewModel,
    currentPlot: Plot
) {
    val context = LocalContext.current
    val grower by viewModel.selectedGrower
    val group by viewModel.selectedPlotGroup

    val themeColor = Color(0xFFFFA000)

    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var totalYield by remember { mutableStateOf("") }

    val allMachines = viewModel.harvestMachines

    var selectedMachineNames by remember { mutableStateOf(listOf<String>()) }

    val hoursMap = remember { mutableStateMapOf<String, String>() }

    var showMachineSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // --- HEADERS ---
        InfoHeader(label = "Grower", value = grower?.name ?: "-", backgroundColor = Color(0xFFFFF8E1))
        InfoHeader(label = "Plot Group", value = group?.name ?: "-", backgroundColor = Color(0xFFFFECB3))
        InfoHeader(label = "Plot", value = currentPlot.name, backgroundColor = Color(0xFFFFF8E1))

        Spacer(modifier = Modifier.height(24.dp))

        // --- TITLE ---
        Text(
            text = "Harvest Data",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = themeColor,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- FORM FIELDS ---
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            DatePickerFieldHarvest(label = "Start Date", dateValue = startDate, onDateSelected = { startDate = it })
            Spacer(modifier = Modifier.height(16.dp))
            DatePickerFieldHarvest(label = "End Date", dateValue = endDate, onDateSelected = { endDate = it })

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = area,
                onValueChange = { if (it.isNumber()) area = it },
                label = { Text("Harvested Area") },
                suffix = { Text("ha") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = totalYield,
                onValueChange = { if (it.isNumber()) totalYield = it },
                label = { Text("Total Yield") },
                suffix = { Text("tons") }, // Или kg
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Machines & Usage", fontWeight = FontWeight.Bold, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { showMachineSheet = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (selectedMachineNames.isEmpty()) "SELECT MACHINES" else "ADD/REMOVE MACHINES")
            }

            if (selectedMachineNames.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                selectedMachineNames.forEach { machineName ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)), // Светло оранжево
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = machineName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            OutlinedTextField(
                                value = hoursMap[machineName] ?: "",
                                onValueChange = { newValue ->
                                    if (newValue.isNumber()) {
                                        hoursMap[machineName] = newValue
                                    }
                                },
                                label = { Text("Hours") },
                                modifier = Modifier.width(100.dp),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- SAVE BUTTON ---
            Button(
                onClick = {

                    if (startDate.isEmpty() || area.isEmpty() || totalYield.isEmpty()) {
                        Toast.makeText(context, "Please fill generic fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (selectedMachineNames.isEmpty()) {
                        Toast.makeText(context, "Please select at least one machine", Toast.LENGTH_SHORT).show()
                        return@Button
                    }


                    val machineUsageList = selectedMachineNames.map { name ->
                        val hours = hoursMap[name]?.toDoubleOrNull() ?: 0.0
                        MachineUsageData(machineName = name, hoursUsed = hours)
                    }

                    val report = HarvestReport(
                        plotId = currentPlot.id,
                        plotName = currentPlot.name,
                        startDate = startDate,
                        endDate = endDate,
                        area = area.toDoubleOrNull() ?: 0.0,
                        totalYield = totalYield.toDoubleOrNull() ?: 0.0,
                        machines = machineUsageList
                    )

                    viewModel.saveHarvestReport(report)
                    Toast.makeText(context, "Harvest Data Saved!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = themeColor)
            ) {
                Text("SAVE REPORT")
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }

    if (showMachineSheet) {
        SearchableMachineSheet(
            allMachines = allMachines,
            alreadySelected = selectedMachineNames,
            onDismiss = { showMachineSheet = false },
            onSelectionChanged = { newSelection ->
                selectedMachineNames = newSelection
                val currentKeys = hoursMap.keys.toList()
                currentKeys.forEach { key ->
                    if (!newSelection.contains(key)) {
                        hoursMap.remove(key)
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableMachineSheet(
    allMachines: List<String>,
    alreadySelected: List<String>,
    onDismiss: () -> Unit,
    onSelectionChanged: (List<String>) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }


    val filteredMachines = remember(searchQuery, allMachines) {
        if (searchQuery.isEmpty()) allMachines
        else allMachines.filter { it.contains(searchQuery, ignoreCase = true) }
    }


    var currentSelection by remember { mutableStateOf(alreadySelected) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        modifier = Modifier.fillMaxHeight(0.9f) // Висок прозорец
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Select Machines", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            // SEARCH BAR
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search machine...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // LIST
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredMachines) { machine ->
                    val isSelected = currentSelection.contains(machine)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                currentSelection = if (isSelected) {
                                    currentSelection - machine
                                } else {
                                    currentSelection + machine
                                }
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = isSelected, onCheckedChange = null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = machine, fontSize = 16.sp)
                    }
                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // DONE BUTTON
            Button(
                onClick = {
                    onSelectionChanged(currentSelection)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("DONE")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

private fun String.isNumber(): Boolean = this.all { it.isDigit() || it == '.' }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerFieldHarvest(label: String, dateValue: String, onDateSelected: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    OutlinedTextField(
        value = dateValue, onValueChange = {}, label = { Text(label) }, readOnly = true,
        trailingIcon = { IconButton(onClick = { showDialog = true }) { Icon(Icons.Default.DateRange, contentDescription = null) } },
        modifier = Modifier.fillMaxWidth().clickable { showDialog = true }
    )
    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(it)))
                    }
                }) { Text("OK") }
            }
        ) { DatePicker(state = datePickerState) }
    }
}