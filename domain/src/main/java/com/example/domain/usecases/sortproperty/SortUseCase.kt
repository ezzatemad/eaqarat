package com.example.domain.usecases.sortproperty

import com.example.domain.datamodel.sortproperty.SortResponse
import com.example.domain.repository.sort.SortRepo
import javax.inject.Inject

class SortUseCase @Inject constructor(
    private val sortRepo: SortRepo
) {

    suspend fun sort(token: String, sortBy: String, ascending: Boolean): SortResponse {
        return sortRepo.sort(token, sortBy, ascending)
    }
}