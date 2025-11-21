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
fun PlotGroupList(navController: NavController, viewModel: AppViewModel){
    val groups = viewModel.groups
    val currentGrower = viewModel.selectedGrower.value
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title={
                    Text("Plot Groups of ${currentGrower?.name}")
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(groups){
                if(currentGrower?.id == it.ownerID){
                PlotGroupCard(
                    plotGroup = it,
                    onPlotGroupClick = {
                        if(it.active){
                            Log.d("${it.name} status","Active: true")
                        }else{
                            Log.d("${it.name} status","Active: false")
                        }
                    })
                }
            }
        }
    }
}
