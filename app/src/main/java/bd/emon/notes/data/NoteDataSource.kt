package bd.emon.notes.data

import bd.emon.notes.common.Response

interface NoteDataSource {
    suspend fun createNote(title:String,content:String)
}