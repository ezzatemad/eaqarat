package com.example.data.repositoryimpl.getallpropert

import android.media.session.MediaSession.Token
import com.example.data.datasource.getallproperty.GetAllPropertyDataSource
import com.example.domain.datamodel.getallproperty.PropertyResponse
import com.example.domain.repository.getallproperty.GetAllPropertyRepo
import javax.inject.Inject

class GetAllPropertyRepoImpl @Inject constructor(
    private val getAllPropertyDataSource: GetAllPropertyDataSource
) : GetAllPropertyRepo {
    override suspend fun getAllProperty(token: String): PropertyResponse {
        return getAllPropertyDataSource.getAllProperty(token)
    }
}