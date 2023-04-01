package com.kappdev.notes.feature_notes.domain.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.time.LocalDateTime

class AlarmConvertor {

    @TypeConverter
    fun alarmToJson(time: LocalDateTime?): String? {
        return if (time != null) Gson().toJson(time) else null
    }

    @TypeConverter
    fun jsonToAlarm(json: String?): LocalDateTime? {
        return  if (json != null ) Gson().fromJson(json, LocalDateTime::class.java) else null
    }

}