package com.example.notes.presentation.mapper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LocalDateTimeOnViewFormatter @Inject constructor() {

    fun toSimpleDate(localDateTime: LocalDateTime): String =
        localDateTime.format(dateFormatter)

    fun toSimpleDateTime(localDateTime: LocalDateTime): String =
        localDateTime.format(dateTimeFormatter)

    fun fromSimpleDate(dateString: String): LocalDateTime =
        LocalDateTime.parse(dateString, dateFormatter)

    fun fromSimpleDateTime(dateTimeString: String): LocalDateTime =
        LocalDateTime.parse(dateTimeString, dateTimeFormatter)


    private companion object {
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    }
}
