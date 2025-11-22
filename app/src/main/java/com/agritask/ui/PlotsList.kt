package com.agritask.ui

import android.util.Log
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
    val plots = viewModel.plots
    val group = viewModel.selectedPlotGroup.value
    val grower = viewModel.selectedGrower.value
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title={
                Text("Plots from ${group?.name} of ${grower?.name}")
            }
        )
    }) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(plots){
                if(it.groupID==group?.id && it.ownerID==grower?.id){
                    PlotCard(
                        plot =it,
                        onPlotClick = {
                            Log.d("Plot", "To be continue...")
                        }
                    )
                }
            }
        }

    }
}