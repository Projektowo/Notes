package com.example.notes.data.local

import androidx.room.TypeConverter
import com.example.notes.domain.model.DomainCategoryColorType
import com.example.notes.domain.model.DomainNotePriorityType
import java.time.LocalDateTime

class Converters {

    @TypeConverter
    fun toPriorityType(value: String) = enumValueOf<DomainNotePriorityType>(value)

    @TypeConverter
    fun fromPriorityType(value: DomainNotePriorityType) = value.name

    @TypeConverter
    fun toCategoryType(value: String) = enumValueOf<DomainCategoryColorType>(value)

    @TypeConverter
    fun fromCategoryType(value: DomainCategoryColorType) = value.name

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }
}
