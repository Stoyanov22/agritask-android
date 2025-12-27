package com.agritask.ui.task.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.agritask.ui.AppViewModel
import com.agritask.ui.MeteorologyReport
import com.agritask.ui.Plot
import com.agritask.ui.task.screens.InfoHeader

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Meteorology(
    navController: NavController,
    viewModel: AppViewModel,
    currentPlot: Plot
) {
    val context = LocalContext.current
    val grower by viewModel.selectedGrower
    val group by viewModel.selectedPlotGroup

    var precipitation by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var hail by remember { mutableStateOf("") }
    var frost by remember { mutableStateOf("") }

    val themeColor = Color(0xFF0288D1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // --- HEADERS ---
        InfoHeader(label = "Grower", value = grower?.name ?: "-", backgroundColor = Color(0xFFE1F5FE))
        InfoHeader(label = "Plot Group", value = group?.name ?: "-", backgroundColor = Color(0xFFB3E5FC))
        InfoHeader(label = "Plot", value = currentPlot.name, backgroundColor = Color(0xFFE1F5FE))

        Spacer(modifier = Modifier.height(24.dp))

        // --- TITLE ---
        Text(
            text = "Meteorology Data",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = themeColor,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = "Enter weather conditions",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- FIELDS ---
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            OutlinedTextField(
                value = precipitation,
                onValueChange = { if (it.isValidNumber()) precipitation = it },
                label = { Text("Precipitation") },
                suffix = { Text("mm") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = temperature,
                onValueChange = {
                    if (it.isEmpty() || it == "-" || it.replace("-","").isValidNumber()) {
                        temperature = it
                    }
                },
                label = { Text("Temperature") },
                suffix = { Text("°C") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Decimal понякога няма минус на някои клавиатури
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = hail,
                onValueChange = { if (it.isValidNumber()) hail = it },
                label = { Text("Hail") },
                suffix = { Text("mm") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = frost,
                onValueChange = { if (it.isValidNumber()) frost = it },
                label = { Text("Frost damage/risk") },
                suffix = { Text("%") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- SAVE BUTTON ---
            Button(
                onClick = {
                    if (precipitation.isEmpty() || temperature.isEmpty()) {
                        Toast.makeText(context, "Please enter at least Precipitation and Temp", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val report = MeteorologyReport(
                        plotId = currentPlot.id,
                        plotName = currentPlot.name,
                        precipitation = precipitation.toDoubleOrNull() ?: 0.0,
                        temperature = temperature.toDoubleOrNull() ?: 0.0,
                        hail = hail.toDoubleOrNull() ?: 0.0,
                        frost = frost.toDoubleOrNull() ?: 0.0
                    )

                    viewModel.saveMeteorologyReport(report)
                    Toast.makeText(context, "Meteo Data Saved!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = themeColor)
            ) {
                Text("SAVE REPORT")
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

private fun String.isValidNumber(): Boolean {
    return this.all { char -> char.isDigit() || char == '.' }
}