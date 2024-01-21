package com.example.notes.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.notes.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime

@HiltWorker
class NotificationReminderWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters
) : Worker(appContext, params) {
    override fun doWork(): Result = try {

        showReminderNotification(appContext, inputData.getString("type"))
        Result.success()
    } catch (e: Exception) {
        Log.e("NotificationReminderWorker", "doWork: $e")
        Result.failure()
    }

    private fun showReminderNotification(context: Context, type: String? = null) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Note reminder",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Hello!")
            .setContentText("Take a look on your ${type?.lowercase()} priority notes!")
            .setSmallIcon(R.drawable.ic_priority_high)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
            .also { Log.e("TAG", "doWork show notification! $type ${LocalDateTime.now()}") }
    }

    companion object {
        const val CHANNEL_ID = "DailyNotificationsChannel"
        const val NOTIFICATION_ID = 1
    }
}
