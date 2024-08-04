package com.example.data.datasource.sortproperty

import com.example.domain.datamodel.sortproperty.SortResponse

interface SortPropertyDataSource {
    suspend fun sort(token: String, sortBy: String, ascending: Boolean): SortResponse

}