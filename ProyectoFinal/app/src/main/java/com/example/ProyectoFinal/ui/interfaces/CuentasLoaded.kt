package com.example.ProyectoFinal.ui.interfaces

import com.example.ProyectoFinal.models.DataCuentaUser

interface CuentasLoaded {
    fun onLoginLoaded(cuentaUser: ArrayList<DataCuentaUser>?)
    fun onErrorLoading(error: Throwable?, message: String)
}