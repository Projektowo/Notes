package com.example.notes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notes.data.local.model.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity): Long

    @Query("SELECT * FROM table_note WHERE id=:noteId")
    fun getNoteById(noteId: Int): NoteEntity

    @Query("SELECT * FROM table_note ORDER BY id DESC")
    fun observeNotes(): Flow<List<NoteEntity>>

    @Query("DELETE FROM table_note WHERE id=:noteId")
    suspend fun deleteNoteById(noteId: Int)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)
}
