package com.example.credibanco.data.utilApiResponse

data class AuthorizationApiResponse(
    val receiptId: String?,
    val rrn: String?,
    val statusCode: String,
    val statusDescription: String
)
