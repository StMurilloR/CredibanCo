package com.example.credibanco.data.dto

data class AuthorizationVO(
    val id: String,
    val commerceCode: String,
    val terminalCode: String,
    val amount: String,
    val card: String,
)
