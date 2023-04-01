package com.kappdev.notes.feature_notes.domain.model

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val content: AlarmContent
)
