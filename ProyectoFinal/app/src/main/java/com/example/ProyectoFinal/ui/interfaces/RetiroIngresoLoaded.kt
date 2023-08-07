package com.example.ProyectoFinal.ui.interfaces

import com.example.ProyectoFinal.models.DataMovimiento

interface RetiroIngresoLoaded {
    fun onRetiroIngresoLoaded(dataMovimiento: DataMovimiento?)
    fun onErrorLoading(error: Throwable?, message: String)
}