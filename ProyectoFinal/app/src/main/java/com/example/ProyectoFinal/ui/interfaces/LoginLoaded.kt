package com.example.ProyectoFinal.ui.interfaces

import com.example.ProyectoFinal.models.Token

interface LoginLoaded {
    fun onLoginLoaded(token: Token?)
    fun onErrorLoading(error: Throwable?, message: String)
}