package com.example.ProyectoFinal.ui.interfaces

import com.example.ProyectoFinal.models.DataRespuestaTransferencia


interface TransferenciasLoaded {
    fun onTransferenciaLoading(respuestaTransferencia: DataRespuestaTransferencia?)
    fun onErrorLoading(error: Throwable?, message: String)
}