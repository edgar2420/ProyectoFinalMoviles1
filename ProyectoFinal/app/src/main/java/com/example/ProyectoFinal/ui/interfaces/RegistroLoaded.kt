package com.example.ProyectoFinal.ui.interfaces

import com.example.ProyectoFinal.models.DataRespuestaRegistro

interface RegistroLoaded {
    fun onRegistroUsuarioLoaded(dataRespuestaRegistro: DataRespuestaRegistro?)
    fun onErrorLoading(error: Throwable?, message: String)
}