package com.example.notes.worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.notes.domain.model.DomainNotePriorityType
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class WorkerStarter(context: Context) {

    private val workManager = WorkManager.getInstance(context)

    companion object {
        const val WORK_MANAGER_REMINDER_HIGH_TAG = "WORK_MANAGER_REMINDER_HIGH_TAG"
        const val WORK_MANAGER_REMINDER_MEDIUM_TAG = "WORK_MANAGER_REMINDER_MEDIUM_TAG"
    }

    operator fun invoke(repeatIntervalDays: Int, domainNotePriorityType: DomainNotePriorityType) {

        val data = Data.Builder()
        data.putString("type", domainNotePriorityType.name)

        val request = PeriodicWorkRequest.Builder(
            NotificationReminderWorker::class.java,
            repeatIntervalDays.toLong(),
            TimeUnit.DAYS
        ).apply {
            setInputData(data.build())
            setInitialDelay(repeatIntervalDays.toLong(), TimeUnit.DAYS)
            if (domainNotePriorityType == DomainNotePriorityType.HIGH) {
                addTag(WORK_MANAGER_REMINDER_HIGH_TAG)
            } else if (domainNotePriorityType == DomainNotePriorityType.MEDIUM) {
                addTag(WORK_MANAGER_REMINDER_MEDIUM_TAG)
            }
        }.build()

        workManager.enqueue(request)
    }

    fun initReminder(days: Int, domainNotePriorityType: DomainNotePriorityType) {
        if (isWorkerAlreadyRunning()) return
        invoke(days, domainNotePriorityType)
    }

    fun rescheduleReminder(days: Int, domainNotePriorityType: DomainNotePriorityType) {
        cancel(domainNotePriorityType = domainNotePriorityType)
        invoke(days, domainNotePriorityType)
    }

    private fun cancel(domainNotePriorityType: DomainNotePriorityType) {
        if (domainNotePriorityType == DomainNotePriorityType.HIGH) {
            WorkManager.getInstance().cancelAllWorkByTag(WORK_MANAGER_REMINDER_HIGH_TAG)
        } else if (domainNotePriorityType == DomainNotePriorityType.MEDIUM) {
            WorkManager.getInstance().cancelAllWorkByTag(WORK_MANAGER_REMINDER_MEDIUM_TAG)
        }
    }

    private fun isWorkerAlreadyRunning(): Boolean {
        val workInfoHigh = workManager.getWorkInfosByTag(WORK_MANAGER_REMINDER_HIGH_TAG).get()
        val workInfoMedium = workManager.getWorkInfosByTag(WORK_MANAGER_REMINDER_MEDIUM_TAG).get()
        (workInfoHigh + workInfoMedium).forEach { work ->
            if (work.state == WorkInfo.State.ENQUEUED || work.state == WorkInfo.State.RUNNING)
                return true
        }
        return false
    }
}
