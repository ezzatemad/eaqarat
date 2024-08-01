package com.example.domain.datamodel.getallproperty

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItem(

	val price: Double? = null,

	val propertyType: String? = null,

	val imageUrl: String? = null,

	val description: String? = null,

	val location: String? = null,

	val title: String? = null,

	val listedAt: String? = null,

	val status: String? = null
) : Parcelable