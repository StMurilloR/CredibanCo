package com.example.credibanco.utils

/**
 * Clase genérica que contiene el estado de carga de los datos.
 * @param data datos que retorna la ejecución
 * @param message Mensaje correspondiente al estado de carga
 */
sealed class Resource<out T>(val data: T? = null, val message: String? = null, val errorCode: String? = null) {
    /**
     * Notifica carga exitosa de los datos
     * @param data Los datos de respuesta de la ejecución
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Notifica que ocurrio un error en la carga de los datos
     * @param message Mensaje de error
     * @param data Los datos de respuesta de la ejecución
     */
    class Error<T>(errorCode: String) : Resource<T>(errorCode = errorCode)

    /**
     * Notifica que los datos se está cargando
     * @param data Los datos de respuesta de la ejecución
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)

}
