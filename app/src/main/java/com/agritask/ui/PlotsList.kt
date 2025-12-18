package com.agritask.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlotsList(navController: NavController, viewModel: AppViewModel){
    val allPlots = viewModel.plots
    val group = viewModel.selectedPlotGroup.value
    val grower = viewModel.selectedGrower.value

    val filteredPlots = allPlots.filter {
        it.groupID == group?.id && it.ownerID == grower?.id
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title={
                Text("Plots from ${group?.name} of ${grower?.name}")
            }
        )
    }) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(filteredPlots) { plot ->
                PlotCard(
                    plot = plot,
                    onPlotClick = {
                        viewModel.onPlotSelected(plot)
                        navController.navigate("tasks")
                    }
                )
            }
        }
    }
}