package com.example.credibanco.data

import com.example.credibanco.data.dto.AnnulationVO
import com.example.credibanco.data.dto.AuthorizationVO
import com.example.credibanco.data.utilApiResponse.AnnulationApiResponse
import com.example.credibanco.data.utilApiResponse.AuthorizationApiResponse
import com.example.credibanco.utils.EndPoints
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Interfaz mediante la cual se definen los metodos de consulta al API
 */
interface PaymentApi {
    /**
     * Funcion que autoriza las transacciones
     */
    @POST(EndPoints.API_AUTHORIZATION)
    suspend fun postAuthorization(
        @Header("Authorization") authorizationKey: String,
        @Body authorizationVO: AuthorizationVO
    ): AuthorizationApiResponse

    /**
     * Funcion que anula las transacciones
     */
    @POST(EndPoints.API_ANNULATION)
    suspend fun postAnnulation(
        @Header("Authorization") authorizationKey: String,
        @Body annulationVO: AnnulationVO
    ): AnnulationApiResponse
}