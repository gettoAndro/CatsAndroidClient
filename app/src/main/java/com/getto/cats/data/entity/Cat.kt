package com.getto.cats.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cat (
        val id: Long,
        val name : String,
        val imagename : String?
) : Parcelable
