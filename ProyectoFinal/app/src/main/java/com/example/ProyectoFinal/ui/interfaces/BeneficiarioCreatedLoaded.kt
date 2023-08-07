package com.example.ProyectoFinal.ui.interfaces

import com.example.ProyectoFinal.models.DataBeneficiariosList

interface BeneficiarioCreatedLoaded {
    fun onBeneficiarioCreatedLoaded(dataBeneficiario: DataBeneficiariosList?)
    fun onErrorLoading(error: Throwable?, message: String)
}