package com.agritask.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PlotCard(
    plot: Plot,
    onPlotClick: ()->Unit
){
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .clickable{ onPlotClick()},
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Plot icon",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column{
                Text(
                    text = plot.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Active: ${plot.active}"
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "ID: ${plot.id}"
                )
                Text(
                    text = "Code: ${plot.code}"
                )
            }
        }
    }
}