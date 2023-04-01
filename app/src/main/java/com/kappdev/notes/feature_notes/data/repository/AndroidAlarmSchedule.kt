package com.kappdev.notes.feature_notes.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.kappdev.notes.feature_notes.data.AlarmReceiver
import com.kappdev.notes.feature_notes.domain.model.AlarmContent
import com.kappdev.notes.feature_notes.domain.model.AlarmContentType
import com.kappdev.notes.feature_notes.domain.model.AlarmItem
import com.kappdev.notes.feature_notes.domain.repository.AlarmSchedule
import java.time.ZoneId

class AndroidAlarmSchedule(
    private val context: Context
): AlarmSchedule {

    private val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    override fun schedule(item: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            val contentJson = Gson().toJson(item.content)
            putExtra("content", contentJson)
            putExtra("alarmId", codeFrom(item.content))
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            PendingIntent.getBroadcast(
                context,
                codeFrom(item.content),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    /**
     * if note -> code = noteId;
     * if todoList -> code = -todoListId
     * */
    private fun codeFrom(c: AlarmContent) = if (c.type == AlarmContentType.Note) c.id.toInt() else -c.id.toInt()

    override fun cancel(item: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.content.id.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}