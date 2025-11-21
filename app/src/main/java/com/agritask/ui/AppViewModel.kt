package com.agritask.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State

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
    //Plots Groups
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

    private val _selectedGrower = mutableStateOf<Grower?>(null)
    val selectedGrower: State<Grower?> = _selectedGrower

    fun onGrowerSelected(grower: Grower){
        _selectedGrower.value = grower
    }
}