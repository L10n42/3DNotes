package com.kappdev.notes.feature_notes.domain.repository

import com.kappdev.notes.feature_notes.domain.model.AlarmItem

interface AlarmSchedule {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}