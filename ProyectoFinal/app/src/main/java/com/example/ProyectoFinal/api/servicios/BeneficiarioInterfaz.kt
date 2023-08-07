package com.example.ProyectoFinal.api.servicios

import com.example.ProyectoFinal.models.*
import retrofit2.Call
import retrofit2.http.*

interface BeneficiarioInterfaz {

    @POST("/api/recipients")
    fun registrarBeneficiario(
        @Header("AUTHORIZATION") value: String,
        @Body dataBeneficiario: DataBeneficiario
    ): Call<DataBeneficiariosList>

    @GET("/api/recipients")
    fun getBeneficiarios(
        @Header("AUTHORIZATION") value: String
    ): Call<ArrayList<DataBeneficiariosList>>

    @POST("/api/accounts/transfer")
    fun realizarTransferencia(
        @Header("AUTHORIZATION") value: String,
        @Body dataTransferencia: DataTransferencia
    ): Call<DataRespuestaTransferencia>
}