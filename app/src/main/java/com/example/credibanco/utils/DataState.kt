package com.example.credibanco.utils

/**
 * Modelo de datos para el estado de carga de cualquier objeto
 * @param isLoading Para identificar si se esta o no cargando los datos
 * @param error Muestra el error que ocurrió en la adquisición de los datos
 * @param data Contiene los datos que fueron cargados desde la BD
 */
data class DataState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null
)