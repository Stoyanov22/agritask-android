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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.agritask.ui.AppViewModel
import com.agritask.ui.DrillingReport
import com.agritask.ui.Plot
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drilling(
    navController: NavController,
    viewModel: AppViewModel,
    currentPlot: Plot
) {
    val context = LocalContext.current
    val grower by viewModel.selectedGrower
    val group by viewModel.selectedPlotGroup

    val themeColor = Color(0xFF8D6E63)

    // --- STATE ---
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var selectedMachine by remember { mutableStateOf("") }

    val allImplements = viewModel.availableImplements
    var selectedImplements by remember { mutableStateOf(listOf<String>()) }
    var showImplementsSheet by remember { mutableStateOf(false) } // Управлява отварянето на менюто

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        // --- HEADERS ---
        InfoHeader(label = "Grower", value = grower?.name ?: "-", backgroundColor = Color(0xFFEFEBE9))
        InfoHeader(label = "Plot Group", value = group?.name ?: "-", backgroundColor = Color(0xFFD7CCC8))
        InfoHeader(label = "Plot", value = currentPlot.name, backgroundColor = Color(0xFFEFEBE9))

        Spacer(modifier = Modifier.height(24.dp))

        // --- TITLE ---
        Text(
            text = "Drilling / Seeding",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = themeColor,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- FORM ---
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {


            DatePickerField(
                label = "Start Date",
                dateValue = startDate,
                onDateSelected = { startDate = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DatePickerField(
                label = "End Date",
                dateValue = endDate,
                onDateSelected = { endDate = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = area,
                onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) area = it },
                label = { Text("Operation Area") },
                suffix = { Text("ha") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            SimpleDropdown(
                label = "Select Machine",
                options = viewModel.availableMachines,
                selectedOption = selectedMachine,
                onOptionSelected = { selectedMachine = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Implements / Attachments", fontWeight = FontWeight.Bold, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { showImplementsSheet = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.List, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (selectedImplements.isEmpty()) "SELECT IMPLEMENTS" else "EDIT SELECTION")
            }

            if (selectedImplements.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEBE9)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        selectedImplements.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = androidx.compose.material.icons.Icons.Default.Delete, // Иконка за премахване (визуална само тук)
                                    contentDescription = null,
                                    tint = themeColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = item, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- SAVE BUTTON ---
            Button(
                onClick = {
                    if (startDate.isEmpty() || area.isEmpty() || selectedMachine.isEmpty()) {
                        Toast.makeText(context, "Please fill required fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val report = DrillingReport(
                        plotId = currentPlot.id,
                        plotName = currentPlot.name,
                        startDate = startDate,
                        endDate = endDate,
                        area = area.toDoubleOrNull() ?: 0.0,
                        machine = selectedMachine,
                        implements = selectedImplements
                    )

                    viewModel.saveDrillingReport(report)
                    Toast.makeText(context, "Drilling Saved!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = themeColor)
            ) {
                Text("SAVE REPORT")
            }

            Spacer(modifier = Modifier.height(50.dp)) // Място отдолу
        }
    }

    if (showImplementsSheet) {
        ModalBottomSheet(
            onDismissRequest = { showImplementsSheet = false },
            containerColor = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Select Implements",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(allImplements) { implement ->
                        val isSelected = selectedImplements.contains(implement)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Логика за добавяне/премахване
                                    selectedImplements = if (isSelected) {
                                        selectedImplements - implement
                                    } else {
                                        selectedImplements + implement
                                    }
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = null // Управляваме го от Row-a
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = implement, fontSize = 16.sp)
                        }
                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { showImplementsSheet = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("DONE")
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    dateValue: String,
    onDateSelected: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    OutlinedTextField(
        value = dateValue,
        onValueChange = { },
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select Date")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    datePickerState.selectedDateMillis?.let { millis ->
                        // Форматираме датата
                        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                        onDateSelected(formatter.format(Date(millis)))
                    }
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}