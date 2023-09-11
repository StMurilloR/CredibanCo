package com.example.credibanco.domain.use_case


import com.example.credibanco.data.dto.TransactionVO
import com.example.credibanco.domain.repository_definition.Repository
import javax.inject.Inject


class TransactionInsertUseCase @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(transactionVO: TransactionVO) {
        repository.insertTransaction(transactionVO)

    }
}