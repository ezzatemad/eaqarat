package com.example.data.repositoryimpl.sortproperty

import com.example.data.datasource.sortproperty.SortPropertyDataSource
import com.example.domain.datamodel.sortproperty.SortResponse
import com.example.domain.repository.sort.SortRepo
import javax.inject.Inject

class SortRepoImpl@Inject constructor(
    private val sortPropertyDataSource: SortPropertyDataSource
) : SortRepo {
    override suspend fun sort(token: String, sortBy: String, ascending: Boolean): SortResponse {
        return sortPropertyDataSource.sort(token, sortBy, ascending)
    }
}