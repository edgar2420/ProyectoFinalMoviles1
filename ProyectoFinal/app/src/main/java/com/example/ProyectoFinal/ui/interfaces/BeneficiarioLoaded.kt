package com.example.ProyectoFinal.ui.interfaces

import com.example.ProyectoFinal.models.DataBeneficiariosList

interface BeneficiarioLoaded {
    fun onBeneficiarioLoaded(dataBeneficiario: ArrayList<DataBeneficiariosList>?)
    fun onErrorLoading(error: Throwable?, message: String)
}