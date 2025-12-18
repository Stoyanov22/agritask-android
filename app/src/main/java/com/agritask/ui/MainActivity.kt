package com.agritask.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agritask.ui.task.screens.Fertilization
import com.agritask.ui.task.screens.Scouting
import com.agritask.ui.task.screens.SprayingScreen
import com.agritask.ui.theme.AgritaskAndroidTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgritaskAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val appViewModel: AppViewModel = viewModel()
                   val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "login" ,
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable(route = "login"){
                            LoginScreen(navController = navController)
                        }
                        composable(route = "main"){
                            MainScreen(navController = navController)
                        }
                        composable(route = "growers"){
                            GrowersList(
                                navController = navController,
                                viewModel = appViewModel
                            )
                        }
                        composable(route = "plot_groups"){
                            PlotGroupList(
                                navController = navController,
                                viewModel = appViewModel
                            )
                        }
                        composable(route = "plots"){
                            PlotsList(
                                navController = navController,
                                viewModel = appViewModel
                            )
                        }
                        composable(route = "tasks") {
                            TaskListScreen(navController = navController, viewModel = appViewModel)
                        }

                        composable(route = "spraying") {
                            val plot = appViewModel.selectedPlot.value
                            if (plot != null) {
                                SprayingScreen(
                                    navController = navController,
                                    viewModel = appViewModel,
                                    currentPlot = plot
                                )
                            }
                        }
                        composable(route = "scouting") {
                            val plot = appViewModel.selectedPlot.value
                            if (plot != null) {
                                Scouting(navController = navController,viewModel = appViewModel, currentPlot = plot)
                            }
                        }

                        composable(route = "fertilization") {
                            val plot = appViewModel.selectedPlot.value
                            if (plot != null) {
                                Fertilization(navController = navController,viewModel = appViewModel, currentPlot = plot)
                            }
                        }
                    }
                }
            }
        }
    }
}