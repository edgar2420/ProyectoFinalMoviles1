package com.example.ProyectoFinal.ui.interfaces

import com.example.ProyectoFinal.models.DataExtracto

interface ExtractosLoaded {
    fun onExtractosLoaded(extractosCuenta: ArrayList<DataExtracto>?)
    fun onErrorLoading(error: Throwable?, message: String)
}