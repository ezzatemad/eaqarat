package com.example.data.datasourceimple.search

import com.example.data.apiservice.search.SearchApiService
import com.example.data.datasource.search.SearchDataSource
import com.example.domain.datamodel.searchproperty.SearchResponse
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchDataSource {
    override suspend fun search(token: String, query: String): SearchResponse {
        return searchApiService.getAllProperty(token, query)
    }
}