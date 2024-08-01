package com.example.domain.repository.getallproperty

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PropertyResponse(

	val timeStamp: String? = null,

	val data: List<DataItem?>? = null,

	val message: String? = null,

	val statusCode: Int? = null
) : Parcelable