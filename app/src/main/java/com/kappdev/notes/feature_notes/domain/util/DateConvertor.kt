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
        val date = timestamp * 1000L

        return when {
            isCurrentYear(timestamp) && isCurrentDay(timestamp) -> currentDayDateFormat.format(date)
            isCurrentYear(timestamp) -> currentYearDateFormat.format(date)
            else -> fullDateFormat.format(date)
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
        calendar.timeInMillis = timestamp * 1000L
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
        calendar.timeInMillis = timestamp * 1000L
        return calendar.get(Calendar.YEAR)
    }
}