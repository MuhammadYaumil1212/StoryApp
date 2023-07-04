package org.d3if00001.storyapp.data.database.Item

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName="story")
data class StoryResponseItem(
    @PrimaryKey
    @field:SerializedName("id")
    val id:String,
    @field:SerializedName("createdAt")
    val createdAt:String,
    @field:SerializedName("description")
    val description:String,
    @field:SerializedName("photoUrl")
    val photo:String,
    @field:SerializedName("lat")
    val lat:Double?=null,
    @field:SerializedName("lon")
    val lon:Double?=null
)
