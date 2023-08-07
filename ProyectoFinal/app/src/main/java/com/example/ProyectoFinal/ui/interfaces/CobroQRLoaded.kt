package com.example.ProyectoFinal.ui.interfaces

import com.example.ProyectoFinal.models.DataRespuestaCobroQR

interface CobroQRLoaded {
    fun onCobroQRLoaded(dataRespuestaQR: DataRespuestaCobroQR?)
    fun onErrorLoading(error: Throwable?, message: String)
}