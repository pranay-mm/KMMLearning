package com.example.kmmlearning

import kotlinx.datetime.*

fun daysUntilYear():Int{
    val today = Clock.System.todayAt(TimeZone.currentSystemDefault())
    val closeToNewYear = LocalDate(today.year+1,1,1)
    return today.daysUntil(closeToNewYear)
}