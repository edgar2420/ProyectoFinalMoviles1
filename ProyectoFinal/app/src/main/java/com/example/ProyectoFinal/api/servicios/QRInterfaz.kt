package com.example.ProyectoFinal.api.servicios

import com.example.ProyectoFinal.models.DataCobroQR
import com.example.ProyectoFinal.models.DataPagoQR
import com.example.ProyectoFinal.models.DataQRPagoRespuesta
import com.example.ProyectoFinal.models.DataRespuestaCobroQR
import retrofit2.Call
import retrofit2.http.*

interface QRInterfaz {

    @POST("/api/qr/generate")
    fun generarQR(
        @Header("AUTHORIZATION") value: String,
        @Body datosCobroQR: DataCobroQR
    ): Call<DataRespuestaCobroQR>

    fun pagoQR(
        @Header("AUTHORIZATION") value: String,
        @Body dataPagoQR: DataPagoQR
    ): Call<DataQRPagoRespuesta>




}