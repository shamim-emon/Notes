package bd.emon.notes.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var content: String
)
