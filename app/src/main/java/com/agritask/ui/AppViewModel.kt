package com.agritask.ui

import Task
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf

class AppViewModel: ViewModel() {

    //Growers
    val growers = listOf(
        Grower(5124123,"Huan Memo", true,"TL34213", mapOf(
            "country" to "bulgaria",
            "language" to "english"
        )),
        Grower(5345234,"Pedro Medro", true,"TL54233", mapOf(
            "country" to "Mexico",
            "language" to "Spanish"
        )),
        Grower(5123214,"Hose Mose", true,"TL43212", mapOf(
            "country" to "Mexico",
            "language" to "Spanish"
        )),
        Grower(5421233,"Ricardo Cardo", true,"TL43123",mapOf(
            "country" to "Mexico",
            "language" to "Spanish"
        )),
        Grower(1253231,"Johny Dep", true,"TL532413",mapOf(
            "country" to "Mexico",
            "language" to "Spanish"
        )),
        Grower(5431232,"John Dow", true,"TL312542",mapOf(
            "country" to "Mexico",
            "language" to "Spanish"
        )),
        Grower(4324123,"Bebo Mebo", true,"TL423523",mapOf(
            "country" to "Mexico",
            "language" to "Spanish"
        )),
    )
    //Plot Groups
    val groups = listOf(
        PlotGroup(321781,"Plot Group A",5124123,true,"PG31241"),
        PlotGroup(321782,"Plot Group B",5345234,true,"PG31242"),
        PlotGroup(321783,"Plot Group C",5123214,true,"PG31243"),
        PlotGroup(321784,"Plot Group D",5421233,true,"PG31244"),
        PlotGroup(321785,"Plot Group E",1253231,true,"PG31245"),
        PlotGroup(321786,"Plot Group F",5431232,true,"PG31246"),
        PlotGroup(321787,"Plot Group G",4324123,true,"PG31247"),
        PlotGroup(321788,"Plot Group H",5421233,true,"PG31248"),
        PlotGroup(321789,"Plot Group E",1253231,true,"PG31249"),
        PlotGroup(321780,"Plot Group F",5124123,false,"PG31240"),
    )
    //Plots
    val plots = listOf(
        Plot(4893241,"Plot A", true,"P321321",5124123,321781),
        Plot(4893242,"Plot B", true,"P321322",5345234,321782),
        Plot(4893243,"Plot C", true,"P321323",5123214,321783),
        Plot(4893244,"Plot D", true,"P321324",5421233,321784),
        Plot(4893245,"Plot E", true,"P321325",1253231,321785),
        Plot(4893246,"Plot N", true,"P321326",5431232,321786),
        Plot(4893247,"Plot G", true,"P321327",4324123,321787),
        Plot(4893248,"Plot H", true,"P321328",5421233,321788),
        Plot(4893249,"Plot F", true,"P321329",1253231,321789),
        Plot(4893240,"Plot O", true,"P321320",5124123,321780),
        Plot(4893251,"Plot P", true,"P321311",5124123,321781),
        Plot(4893261,"Plot Q", true,"P321321",5345234,321782),
        Plot(4893271,"Plot R", true,"P321331",5123214,321783),
        Plot(4893281,"Plot S", true,"P321341",5421233,321784),
        Plot(4893291,"Plot T", true,"P321351",1253231,321785),
        Plot(4893201,"Plot G", true,"P321361",5431232,321786),
        Plot(4893211,"Plot W", true,"P321371",4324123,321787),
        Plot(4893221,"Plot Z", true,"P321381",5421233,321788),
        Plot(4893231,"Plot X", true,"P321391",1253231,321789),
        Plot(4893262,"Plot Y", true,"P321301",5124123,321780),
    )
    //Tasks
    private val allTasks = listOf(
        Task(101, "Weekly Spraying", TaskType.SPRAYING, listOf(4893241,4893242,4893243,4893244,4893245,4893246,4893247,4893248,4893249,4893240,4893251,4893261,4893271,4893281,4893291,4893201,4893211,4893221,4893231,4893262), "18.12.2025"),
        Task(102, "Morning Scouting", TaskType.SCOUTING, listOf(4893241,4893242,4893243,4893244,4893245,4893246,4893247,4893248,4893249,4893240,4893251,4893261,4893271,4893281,4893291,4893201,4893211,4893221,4893231,4893262), "19.12.2025"),
        Task(103, "Fertilization", TaskType.FERTILIZATION, listOf(4893241,4893242,4893243,4893244,4893245,4893246,4893247,4893248,4893249,4893240,4893251,4893261,4893271,4893281,4893291,4893201,4893211,4893221,4893231,4893262), "20.12.2025")
    )



    private val _selectedGrower = mutableStateOf<Grower?>(null)
    val selectedGrower: State<Grower?> = _selectedGrower

    fun onGrowerSelected(grower: Grower){
        _selectedGrower.value = grower
    }

    private val _selectedPlotGroup = mutableStateOf<PlotGroup?>(value = null)
    val selectedPlotGroup: State<PlotGroup?> = _selectedPlotGroup
    fun onPlotGroupSelected(plotGroup: PlotGroup){
        _selectedPlotGroup.value = plotGroup
    }
    private val _selectedPlot = mutableStateOf<Plot?>(null)
    val selectedPlot: State<Plot?> = _selectedPlot

    fun onPlotSelected(plot: Plot) {
        _selectedPlot.value = plot
    }

    private val _selectedTask = mutableStateOf<Task?>(null)
    val filteredTasks = derivedStateOf {
        val currentPlotId = _selectedPlot.value?.id ?: return@derivedStateOf emptyList()
        allTasks.filter { task -> task.plotIds.contains(currentPlotId) }
    }
    val applicationMethods = listOf("Option A", "Option B", "Option C", "Option D")
    val targetLists = listOf("Target A", "Target B", "Target C", "Target D")
    val areaUnits = listOf("ha", "ac")
    //SCOUTING
    val observationTypes = listOf("Insects (Насекоми)", "Disease (Болест)", "Weeds (Плевели)", "General Growth (Общ растеж)")
    val severityLevels = listOf("Low (Ниско)", "Medium (Средно)", "High (Високо)", "Critical (Критично)")
    //FERT
    val fertilizers = listOf("Urea 46%", "NPK 14-14-14", "Ammonium Nitrate", "DAP")
    val fertilizerUnits = listOf("kg", "L", "ton")

    //ЗА SAVE ГЛЕДАЙ след този коментар
    private val _reports = mutableListOf<TaskReport>()

    fun saveSprayingReport(report: SprayingReport) {
        _reports.add(report) // Тук си слагаш базата данни
        println("SAVED SPRAYING: $report")
    }

    fun saveScoutingReport(report: ScoutingReport) {
        _reports.add(report) // Тук си слагаш базата данни
        println("SAVED SCOUTING: $report")
    }

    fun saveFertilizationReport(report: FertilizationReport) {
        _reports.add(report) // Тук си слагаш базата данни
        println("SAVED FERTILIZATION: $report")
    }

    fun getAllReports(): List<TaskReport> = _reports // Ако искаш си ги извикай някъде
}