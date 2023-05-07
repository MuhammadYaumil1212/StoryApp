package org.d3if00001.storyapp.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity("notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")var id : Int? = 0,
    @ColumnInfo("userId")var userId : Int? = 0,
    @ColumnInfo("imageStory") var imageStory : String?="",
    @ColumnInfo("titleNotes") val titleNotes : String,
    @ColumnInfo("description") val description : String,
    @ColumnInfo("bodyNotes") val bodyNotes : String,
    @ColumnInfo("date") val date : Date?
)
