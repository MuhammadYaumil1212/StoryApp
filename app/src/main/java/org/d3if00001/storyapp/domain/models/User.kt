package org.d3if00001.storyapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String? = null,
    var email: String?=null,
    var password: String?=null,
    var token : String?=null
) : Parcelable
