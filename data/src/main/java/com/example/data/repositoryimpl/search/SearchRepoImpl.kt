package com.example.data.repositoryimpl.search

import com.example.data.datasource.search.SearchDataSource
import com.example.domain.datamodel.searchproperty.SearchResponse
import com.example.domain.repository.search.SearchRepo
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val searchDataSource: SearchDataSource
) : SearchRepo {
    override suspend fun search(token: String, query: String): SearchResponse {
        return searchDataSource.search(token, query)
    }
}