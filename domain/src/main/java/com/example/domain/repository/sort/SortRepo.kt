package com.example.domain.repository.sort

import com.example.domain.datamodel.sortproperty.SortResponse

interface SortRepo {

    suspend fun sort(token: String, sortBy: String, ascending: Boolean): SortResponse
}