package com.example.credibanco.domain.use_case

import com.example.credibanco.domain.repository_definition.Repository
import com.example.credibanco.utils.BAD_REQUEST
import com.example.credibanco.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Caso de uso de las trasacciones
 */
class TransactionsGetAllUseCase @Inject constructor(
    private val repository: Repository
){
    operator fun invoke(): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading(null))
            val transactionsJson = repository.getAllTransactions()
            if (transactionsJson.isNotEmpty()){
                emit(Resource.Success(transactionsJson))
            }else{
                emit(Resource.Error(BAD_REQUEST.toString()))
            }
        }
        catch (e: Exception){
            emit(Resource.Error(BAD_REQUEST.toString()))
        }
    }
}