package com.example.data.repositoryimpl.filterproperty

import com.example.data.datasource.filterproperty.GetFilterPropertyDataSource
import com.example.domain.datamodel.filterproperty.FilterPropertyResponse
import com.example.domain.repository.filterproperty.GetFilterPropertyRepo
import javax.inject.Inject

class GetFilterPropertyRepoImpl @Inject constructor(
    private val getFilterPropertyDataSource: GetFilterPropertyDataSource
) : GetFilterPropertyRepo {
    override suspend fun getFilterProperty(
        token: String,
        title: String,
        type: String
    ): FilterPropertyResponse {
        return getFilterPropertyDataSource.getFilterProperty(token, title, type)
    }
}