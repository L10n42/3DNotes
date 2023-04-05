package com.kappdev.notes.feature_notes.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import com.kappdev.notes.MainActivity
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.domain.model.AlarmContent
import com.kappdev.notes.feature_notes.domain.model.AlarmContentType
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject
    @Named("singletonNotesRepository")
    lateinit var repository: NotesRepository
    private var contentJson = ""

    override fun onReceive(context: Context?, intent: Intent?) {

        contentJson = intent?.getStringExtra("content") ?: return
        val alarmId = intent.getIntExtra("alarmId", 0)
        val alarmContent = Gson().fromJson(contentJson, AlarmContent::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            when (alarmContent.type) {
                AlarmContentType.Note -> {
                    val note = repository.getNoteById(alarmContent.id)
                    removeAlarmFromNote(note)
                    createNotification(context!!, alarmId, note.title, note.content)
                }
                AlarmContentType.TodoList -> {
                    val todoList = repository.getTodoListById(alarmContent.id)
                    removeAlarmFromTodoList(todoList)
                    createNotification(
                        context = context!!,
                        id = alarmId,
                        title = todoList.name,
                        shortMessage = todoList.content.last().toSpannedString(),
                        fullMessage = todoList.toSpannedString()
                    )
                }
            }
        }
    }

    private suspend fun removeAlarmFromNote(note: Note) {
        repository.insertNote(
            note.copy(alarm = null)
        )
    }

    private suspend fun removeAlarmFromTodoList(todoList: TodoList) {
        repository.insertTodoList(
            todoList.copy(alarm = null)
        )
    }

    private fun createNotification(
        context: Context,
        id: Int,
        title: String,
        shortMessage: CharSequence,
        fullMessage: CharSequence = shortMessage
    ) {
        val builder = NotificationCompat.Builder(context, "default").apply {
            setSmallIcon(R.drawable.ic_baseline_notifications_24)
            setContentTitle(title)
            setContentText(shortMessage)
            setAutoCancel(true)
            setCategory(NotificationCompat.CATEGORY_ALARM)
            setStyle(bigMessage(fullMessage))
            setContentIntent(getPaddingIntent(context))
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            priority = NotificationCompat.PRIORITY_HIGH
            setDefaults(NotificationCompat.DEFAULT_LIGHTS)
            setDefaults(NotificationCompat.DEFAULT_SOUND)
            setDefaults(NotificationCompat.DEFAULT_VIBRATE)
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        registerNotificationChannel(notificationManager)
        notificationManager.notify(id, builder.build())
    }

    private fun bigMessage(msg: CharSequence): NotificationCompat.BigTextStyle {
        return NotificationCompat.BigTextStyle().bigText(msg)
    }

    private fun getPaddingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("content", contentJson)
        }
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun registerNotificationChannel(manager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = createNotificationChannel()
            manager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): NotificationChannel {
        return NotificationChannel("default", "Note notification", NotificationManager.IMPORTANCE_HIGH).apply {
            enableVibration(true)
            enableLights(true)
            setSound(
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
            )
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        }
    }
}