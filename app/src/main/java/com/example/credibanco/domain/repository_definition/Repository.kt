package com.example.credibanco.domain.repository_definition

import com.example.credibanco.data.database.entity.TransactionEntity
import com.example.credibanco.data.dto.*
import com.example.credibanco.data.utilApiResponse.AnnulationApiResponse
import com.example.credibanco.data.utilApiResponse.AuthorizationApiResponse

/**
 * Definición del repositorio de las transacciones
 */
interface Repository {
    /**
     * Funcion que obtiene la respuesta de Authorization
     */
    suspend fun postAuthorization(authorizationKey: String, authorizationVO: AuthorizationVO): AuthorizationApiResponse

    /**
     * Funcion que obtiene la respuesta de Annulation
     */
    suspend fun postAnnulation(authorizationKey: String, annulationVO: AnnulationVO): AnnulationApiResponse

    /**
     * Funcion que inserta las transacciones
     */
    suspend fun insertTransaction(transactionVO: TransactionVO)

    /**
     * Funcion que trae las transacciones
     */
    suspend fun getAllTransactions():List<TransactionEntity>

    /**
     * Funcion que actualiza la transaccion
     */
    suspend fun updateTransaction(transactionEntity: TransactionEntity)
}