package org.d3if00001.storyapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Notes(
    val imageStory : String,
    val titleNotes : String,
    val description : String,
    val bodyNotes : String,
    val date : String
) : Parcelable
