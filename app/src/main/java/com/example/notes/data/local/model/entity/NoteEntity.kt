package com.example.notes.data.local.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.domain.model.DomainCategoryColorType
import com.example.notes.domain.model.DomainNotePriorityType
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
@Entity(tableName = "table_note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String? = "",
    @ColumnInfo(name = "content")
    val content: String? = "",
    @ColumnInfo(name = "priority")
    val priority: DomainNotePriorityType? = DomainNotePriorityType.UNKNOWN,
    @ColumnInfo(name = "category")
    val category: DomainCategoryColorType? = DomainCategoryColorType.PURPLE,
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime? = LocalDateTime.now(),
) : Parcelable
