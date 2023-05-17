package org.d3if00001.storyapp.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id") var id:Int = 0,
    @ColumnInfo(name = "name") var name:String?,
    @ColumnInfo(name="email") var email:String?,
    @ColumnInfo(name = "password") var password:String?
)