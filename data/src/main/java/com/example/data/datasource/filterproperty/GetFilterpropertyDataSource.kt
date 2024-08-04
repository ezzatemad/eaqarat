package com.example.data.datasource.filterproperty

import com.example.domain.datamodel.filterproperty.FilterPropertyResponse


interface GetFilterPropertyDataSource {

    suspend fun getFilterProperty(
        token: String,
        title: String,
        type: String
    ): FilterPropertyResponse
}