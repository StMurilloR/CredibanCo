package com.example.credibanco.domain.use_case

import com.example.credibanco.data.dto.AnnulationVO
import com.example.credibanco.data.utilApiResponse.AnnulationApiResponse
import com.example.credibanco.domain.repository_definition.Repository
import com.example.credibanco.utils.APPROVED
import com.example.credibanco.utils.BAD_REQUEST
import com.example.credibanco.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnnulationUseCase @Inject constructor(
    private val repository: Repository
){
    operator fun invoke(authorizationKey: String, annulationVO: AnnulationVO): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading(null))
            val annulationJson = repository.postAnnulation(authorizationKey, annulationVO)
            val annulationResponse = AnnulationApiResponse(statusCode = annulationJson.statusCode, statusDescription = annulationJson.statusDescription)
            if (annulationJson.statusCode == APPROVED) {
                emit(Resource.Success(annulationResponse))
            } else {
                emit(Resource.Error(annulationResponse.statusCode))
            }
        }
        catch (e: Exception){
            emit(Resource.Error(BAD_REQUEST.toString()))
        }
    }
}