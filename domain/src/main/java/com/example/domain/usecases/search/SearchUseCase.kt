package com.example.domain.usecases.search

import com.example.domain.datamodel.searchproperty.SearchResponse
import com.example.domain.repository.search.SearchRepo
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepo: SearchRepo
) {
    suspend fun search(token: String, query: String): SearchResponse {
        return searchRepo.search(token, query)
    }
}