package com.example.credibanco.domain.use_case

import com.example.credibanco.data.dto.AuthorizationVO
import com.example.credibanco.data.utilApiResponse.AuthorizationApiResponse
import com.example.credibanco.domain.repository_definition.Repository
import com.example.credibanco.utils.APPROVED
import com.example.credibanco.utils.BAD_REQUEST
import com.example.credibanco.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthorizationUseCase @Inject constructor(
    private val repository: Repository
){
    operator fun invoke(authorizationKey: String, authorizationVO: AuthorizationVO): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading(null))
            val authorizationJson = repository.postAuthorization(authorizationKey, authorizationVO)
            val annulationResponse = AuthorizationApiResponse(receiptId = authorizationJson.receiptId, rrn = authorizationJson.rrn,
                statusCode = authorizationJson.statusCode, statusDescription = authorizationJson.statusDescription)
            if (authorizationJson.statusCode == APPROVED) {
                emit(Resource.Success(annulationResponse))
            } else {
                emit(Resource.Error(annulationResponse.statusDescription))
            }
        } catch (e: Exception){
            emit(Resource.Error(BAD_REQUEST.toString()))
        }
    }
}