package com.example.data

import java.time.LocalDate

data class GridDay(
    val dayNumber: Int,
    val isCurrentMonth: Boolean,
    val localDate: LocalDate
)
