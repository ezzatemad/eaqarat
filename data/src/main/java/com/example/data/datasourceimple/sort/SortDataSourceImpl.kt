package com.example.data.datasourceimple.sort

import com.example.data.apiservice.sortproperty.SortPropertyApiService
import com.example.data.datasource.sortproperty.SortPropertyDataSource
import com.example.domain.datamodel.sortproperty.SortResponse
import javax.inject.Inject

class SortDataSourceImpl @Inject constructor(
    private val sortPropertyApiService: SortPropertyApiService
) : SortPropertyDataSource {
    override suspend fun sort(token: String, sortBy: String, ascending: Boolean): SortResponse {
        return sortPropertyApiService.getSortedProperties(token, sortBy, ascending)
    }
}