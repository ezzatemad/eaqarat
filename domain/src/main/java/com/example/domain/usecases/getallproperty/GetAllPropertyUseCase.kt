package com.example.domain.usecases.getallproperty

import com.example.domain.datamodel.getallproperty.PropertyResponse
import com.example.domain.repository.getallproperty.GetAllPropertyRepo
import javax.inject.Inject

class GetAllPropertyUseCase @Inject constructor(
    private val getAllPropertyRepo: GetAllPropertyRepo
) {

    suspend fun getAllProperty(token: String): PropertyResponse {
        return getAllPropertyRepo.getAllProperty(token)
    }
}