package com.example.data.datasourceimple.filterproperty.GetFilterProperty

import com.example.data.apiservice.filterproperty.FilterPropertyApiService
import com.example.data.datasource.filterproperty.GetFilterPropertyDataSource
import com.example.domain.datamodel.filterproperty.FilterPropertyResponse
import javax.inject.Inject

class getFilterPropertyDataSourceImple @Inject constructor(
    private val filterPropertyApiService: FilterPropertyApiService
) : GetFilterPropertyDataSource {
    override suspend fun getFilterProperty(
        token: String,
        title: String,
        type: String
    ): FilterPropertyResponse {
        return filterPropertyApiService.getFilterProperty(token, title, type)
    }
}