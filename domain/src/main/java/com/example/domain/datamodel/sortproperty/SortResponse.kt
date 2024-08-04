package com.example.domain.datamodel.sortproperty

import android.os.Parcelable
import com.example.domain.datamodel.getallproperty.DataItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class SortResponse(

	val timeStamp: String? = null,

	val data: List<DataItem?>? = null,

	val message: String? = null,

	val statusCode: Int? = null
):Parcelable