package com.example.domain.repository.filterproperty

import com.example.domain.datamodel.filterproperty.FilterPropertyResponse

interface GetFilterPropertyRepo {

    suspend fun getFilterProperty(
        token: String,
        title: String,
        type: String
    ): FilterPropertyResponse

}