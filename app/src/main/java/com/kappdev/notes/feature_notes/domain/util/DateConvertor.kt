package com.kappdev.notes.feature_notes.domain.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateConvertor {

    @SuppressLint("ConstantLocale")
    private val fullDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
    @SuppressLint("ConstantLocale")
    private val currentYearDateFormat = SimpleDateFormat("dd MMMM, HH:mm", Locale.getDefault())
    @SuppressLint("ConstantLocale")
    private val currentDayDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun getDateString(timestamp: Long): String {
        return when {
            isCurrentYear(timestamp) && isCurrentDay(timestamp) -> currentDayDateFormat.format(timestamp)
            isCurrentYear(timestamp) -> currentYearDateFormat.format(timestamp)
            else -> fullDateFormat.format(timestamp)
        }
    }

    private fun isCurrentDay(timestamp: Long): Boolean {
        val timestampDay = getDayFrom(timestamp)
        val currentDay = getCurrentDay()
        return timestampDay == currentDay
    }

    private fun getCurrentDay() = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

    private fun getDayFrom(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar.get(Calendar.DAY_OF_YEAR)
    }

    private fun isCurrentYear(timestamp: Long): Boolean {
        val timestampYear = getYearFrom(timestamp)
        val currentYear = getCurrentYear()
        return timestampYear == currentYear
    }

    private fun getCurrentYear() = Calendar.getInstance().get(Calendar.YEAR)

    private fun getYearFrom(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar.get(Calendar.YEAR)
    }
}