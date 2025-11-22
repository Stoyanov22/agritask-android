package com.agritask.ui

data class Plot(
    val id: Long,
    val name: String,
    val active: Boolean,
    val code: String,
    val ownerID: Long,
    val groupID: Long
)
