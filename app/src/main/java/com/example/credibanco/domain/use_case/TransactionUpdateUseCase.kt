package com.example.credibanco.domain.use_case

import com.example.credibanco.data.database.entity.TransactionEntity
import com.example.credibanco.domain.repository_definition.Repository
import javax.inject.Inject

/**
 * Caso de uso de las transacciones a actualizar
 */
class TransactionUpdateUseCase @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(transaction: TransactionEntity) {
        repository.updateTransaction(transaction)

    }
}