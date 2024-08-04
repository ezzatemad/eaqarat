package com.example.domain.repository.search

import com.example.domain.datamodel.searchproperty.SearchResponse

interface SearchRepo {

    suspend fun search(token: String, query: String):SearchResponse
}