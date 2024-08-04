package com.example.domain.usecases.filterproperty

import com.example.domain.datamodel.filterproperty.FilterPropertyResponse
import com.example.domain.repository.filterproperty.GetFilterPropertyRepo
import javax.inject.Inject

class GetFilterPropertyUseCase @Inject constructor(
    private val getFilterPropertyRepo: GetFilterPropertyRepo
) {
    suspend fun getFilterProperty(
        token: String,
        title: String,
        type: String
    ): FilterPropertyResponse {
        return getFilterPropertyRepo.getFilterProperty(token, title, type)
    }
}