package com.example.data.datasource.search

import com.example.domain.datamodel.searchproperty.SearchResponse

interface SearchDataSource {

    suspend fun search(token: String, query: String): SearchResponse
}