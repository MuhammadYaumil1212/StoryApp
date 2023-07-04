package org.d3if00001.storyapp.data.remote.mediator

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remote_keys")
data class RemoteKeys(
    @PrimaryKey val id:String,
    val prevKey:Int?,
    val nextKey:Int?
)
