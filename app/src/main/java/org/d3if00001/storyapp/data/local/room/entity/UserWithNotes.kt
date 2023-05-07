package org.d3if00001.storyapp.data.local.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithNotes(
    @Embedded val user:User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val notes:List<Notes>
)
