package com.example.ProyectoFinal.ui.interfaces

import com.example.ProyectoFinal.models.DataQRPagoRespuesta

interface PagoQRLoaded {
    fun onPagoQRLoaded(dataQRPagoRespuesta: DataQRPagoRespuesta?)
    fun onErrorLoading(error: Throwable?, message: String)
}