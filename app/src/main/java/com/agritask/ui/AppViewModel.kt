package com.agritask.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import com.agritask.domain.models.Crop
import com.agritask.domain.models.Season
import java.time.LocalDate
@RequiresApi(Build.VERSION_CODES.O)
class AppViewModel: ViewModel() {
    //Crops
    val wheat = Crop(1, "WHT", "Wheat (Пшеница)", LocalDate.of(2023, 1, 1))
    val corn = Crop(2, "CRN", "Corn (Царевица)", LocalDate.of(2023, 1, 1))
    val barley = Crop(3, "BRL", "Barley (Ечемик)", LocalDate.of(2023, 1, 1))
    //Tasks
    val availableTasks = listOf(
        Task(101, "Fungicide Application", TaskType.SPRAYING, listOf(1, 3), "Protect against fungus"),

        Task(102, "Nitrogen Boost", TaskType.FERTILIZATION, listOf(1, 2, 3), "Spring fertilization"),

        Task(103, "Corn Borer Scout", TaskType.SCOUTING, listOf(2), "Check for drilling insects"),

        Task(104, "General Scouting", TaskType.SCOUTING, listOf(1, 2, 3), "Routine check"),

        Task(105, "Report Harvest", TaskType.HARVEST,listOf(1), "Report Harvest"),

        Task(106, "Report Drilling", TaskType.DRILLING, listOf(1), "Report Drilling"),

        Task(107, "Report Meteorology", TaskType.METEOROLOGY, listOf(1), "Report Meteorology")
    )
    //Growers
    val growers = listOf(
        Grower(5124123,"Huan Memo", true,"TL34213", mapOf(
            "country" to "bulgaria",
            "language" to "english"
        )),
        Grower(5345234,"Pedro Medro", true,"TL54233", mapOf(
            "country" to "Mexico",
            "language" to "Spanish"
        ))
    )
    //Plot Groups
    val groups = listOf(
        PlotGroup(321781,"Plot Group A",5124123,true,"PG31241"),
        PlotGroup(321782,"Plot Group B",5345234,true,"PG31242"),
    )
    //Plots
    val plots = listOf(
        Plot(4893241, "Plot A", true, "P1", 5124123, 321781),
        Plot(4893242, "Plot B", true, "P2", 5345234, 321782)
    )
    //Seasons
    val seasons = listOf(
        Season(
            id = 501,
            plotId = 4893241, // Plot A
            activeFrom = LocalDate.now().minusMonths(2), // Започнал преди 2 месеца
            activeUntil = LocalDate.now().plusMonths(4), // Ще свърши след 4
            crop = wheat
        ),
        Season(
            id = 502,
            plotId = 4893242, // Plot B
            activeFrom = LocalDate.now().minusDays(10),
            crop = corn
        )
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
    fun getActiveSeasonForPlot(plotId: Long): Season? {
        val today = LocalDate.now()
        return seasons.find { season ->
            season.plotId == plotId && season.isActive(today)
        }
    }
    fun getTasksForSelectedPlot(): List<Task> {
        val currentPlot = _selectedPlot.value ?: return emptyList()

        val activeSeason = getActiveSeasonForPlot(currentPlot.id) ?: return emptyList()

        val cropId = activeSeason.crop.id

        return availableTasks.filter { task ->
            task.validForCropIds.contains(cropId)
        }
    }
//    fun isPlotActive(plotId: Long): Boolean {
//        return getActiveSeasonForPlot(plotId) != null
//    }

//    private val _selectedTask = mutableStateOf<Task?>(null)
    //Базата данни :)
    val applicationMethods = listOf("Option A", "Option B", "Option C", "Option D")
    val targetLists = listOf("Target A", "Target B", "Target C", "Target D")
    val areaUnits = listOf("ha", "ac")
    //SCOUTING
    val observationTypes = listOf("Insects (Насекоми)", "Disease (Болест)", "Weeds (Плевели)", "General Growth (Общ растеж)")
    val severityLevels = listOf("Low (Ниско)", "Medium (Средно)", "High (Високо)", "Critical (Критично)")
    //FERT
    val fertilizers = listOf("Urea 46%", "NPK 14-14-14", "Ammonium Nitrate", "DAP")
    val fertilizerUnits = listOf("kg", "L", "ton")
    // --- DRILLING DATA ---
    val availableMachines = listOf("John Deere 8R", "Case IH Magnum", "New Holland T8", "Fendt 1000")
    val availableImplements = listOf("Seeder Väderstad", "Harrow Amazone", "Planter Horsch", "Roller Dal-Bo", "Fertilizer Hopper")
    val harvestMachines = listOf(
        "Claas Lexion 8900", "Claas Lexion 770", "Claas Tucano 580",
        "John Deere X9 1100", "John Deere S790", "John Deere T670",
        "New Holland CR10.90", "New Holland CX8.80",
        "Case IH Axial-Flow 9250", "Fendt IDEAL 9T", "Massey Ferguson IDEAL"
    )
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

    fun saveMeteorologyReport(report: MeteorologyReport) {
        _reports.add(report)
        println("SAVED METEOROLOGY: $report")
    }
    fun saveDrillingReport(report: DrillingReport) {
        _reports.add(report)
        println("SAVED DRILLING: $report")
    }

    fun saveHarvestReport(report: HarvestReport) {
        _reports.add(report)
        println("SAVED HARVEST: $report")
    }

    fun getAllReports(): List<TaskReport> = _reports // Ако искаш си ги извикай някъде
}