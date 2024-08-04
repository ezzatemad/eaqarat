package com.example.domain.datamodel.searchproperty

import com.example.domain.datamodel.getallproperty.DataItem

data class SearchResponse(

    val timeStamp: String? = null,

    val data: List<DataItem?>? = null,

    val message: String? = null,

    val statusCode: Int? = null
)