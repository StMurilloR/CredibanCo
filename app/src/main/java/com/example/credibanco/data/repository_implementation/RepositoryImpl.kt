package com.example.credibanco.data.repository_implementation


import com.example.credibanco.domain.repository_definition.Repository
import com.example.credibanco.data.PaymentApi
import com.example.credibanco.data.database.dao.TransactionDao
import com.example.credibanco.data.database.entity.TransactionEntity
import com.example.credibanco.data.dto.*
import com.example.credibanco.data.utilApiResponse.AnnulationApiResponse
import com.example.credibanco.data.utilApiResponse.AuthorizationApiResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val retrofitInstance: PaymentApi,
    private val appDao: TransactionDao
): Repository {
    override suspend fun postAuthorization(
        authorizationKey: String,
        authorizationVO: AuthorizationVO
    ): AuthorizationApiResponse {
        return retrofitInstance.postAuthorization(authorizationKey = authorizationKey,authorizationVO = authorizationVO)
    }

    override suspend fun postAnnulation(
        authorizationKey: String,
        annulationVO: AnnulationVO
    ): AnnulationApiResponse {
        return retrofitInstance.postAnnulation(authorizationKey = authorizationKey, annulationVO = annulationVO)
    }



    override suspend fun insertTransaction(transactionVO: TransactionVO) {
        val transactionEntity = TransactionEntity(
            idTransaction = transactionVO.idTransaction,
            commerceCode = transactionVO.commerceCode,
            terminalCode = transactionVO.terminalCode,
            amount = transactionVO.amount,
            card = transactionVO.card,
            rrn = transactionVO.rrn,
            receiptId = transactionVO.receiptId,
            annulation = transactionVO.annulation,
            authorization = transactionVO.authorization
        )
        appDao.insert(transactionEntity)
    }

    override suspend fun getAllTransactions(): List<TransactionEntity>{
        return appDao.getAll()
    }

    override suspend fun updateTransaction(transaction: TransactionEntity){
        return appDao.updateTransaction(transaction)
    }


}