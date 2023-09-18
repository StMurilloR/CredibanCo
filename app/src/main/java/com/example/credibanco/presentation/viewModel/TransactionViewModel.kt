package com.example.credibanco.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.credibanco.data.database.entity.TransactionEntity
import com.example.credibanco.data.dto.AnnulationVO
import com.example.credibanco.data.dto.AuthorizationVO
import com.example.credibanco.data.dto.TransactionVO
import com.example.credibanco.data.utilApiResponse.AnnulationApiResponse
import com.example.credibanco.data.utilApiResponse.AuthorizationApiResponse
import com.example.credibanco.domain.use_case.*
import com.example.credibanco.utils.DataState
import com.example.credibanco.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase,
    private val annulationUseCase: AnnulationUseCase,
    private val transactionInsertUseCase: TransactionInsertUseCase,
    private val transactionsUseCase: TransactionsGetAllUseCase,
    private val transactionUpdateUseCase: TransactionUpdateUseCase
): ViewModel() {
    private val _transactions = MutableLiveData<DataState<List<TransactionEntity>>>(DataState())
    val transactions = _transactions

    private val _authorization = MutableLiveData<DataState<AuthorizationApiResponse>>(DataState())
    var authorization = _authorization

    private val _annulation = MutableLiveData<DataState<AnnulationApiResponse>>(DataState())
    val annulation = _annulation

    lateinit var transactionVO: TransactionVO
    var transactionIdSelected: Int? = null
    var transactionAnnulation: Boolean? = null


    /**
     * Función que obtiene la autorizaccion
     */
    fun getAuthorization(authorizationKey: String, authorizationVO: AuthorizationVO) {
        authorizationUseCase(authorizationKey, authorizationVO).onEach { resources->
            when(resources){
                is Resource.Success -> _authorization.value = DataState(data = resources.data as AuthorizationApiResponse)
                is Resource.Loading -> _authorization.value = DataState(isLoading = true)
                is Resource.Error -> _authorization.value = DataState(error = resources.errorCode)
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Función que obtiene la autorizaccion
     */
    fun getAnnulation(authorizationKey: String, annulationVO: AnnulationVO) {
        annulationUseCase(authorizationKey, annulationVO).onEach { resources->
            when(resources){
                is Resource.Success -> _annulation.value = DataState(data = resources.data as AnnulationApiResponse)
                is Resource.Loading -> _annulation.value = DataState(isLoading = true)
                is Resource.Error -> _annulation.value = DataState(error = resources.errorCode)
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Función que guarda la transaccion
     */
    fun setTransactionInsert(transactionVO: TransactionVO) {
        viewModelScope.launch {
            transactionInsertUseCase.invoke(transactionVO)
        }
    }

    /**
     * Función que trae la lista de transacciones
     */
    fun getTransactionsList() {
        transactionsUseCase().onEach { resources ->
            when(resources){
                is Resource.Success -> _transactions.value = DataState(data = resources.data as List<TransactionEntity>)
                is Resource.Loading -> _transactions.value = DataState(isLoading = true)
                is Resource.Error -> _transactions.value = DataState(error = resources.errorCode)
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Función que actualiza la transaccion
     */
    fun setTransactionUpdate(transactionEntity: TransactionEntity) {
        viewModelScope.launch {
            transactionUpdateUseCase.invoke(transactionEntity)
        }
    }

    /**
     * Función que actualiza la transaccion en curso
     */
    fun setTransactionRoom(transactionVO: TransactionVO){
        this.transactionVO = transactionVO
    }

}