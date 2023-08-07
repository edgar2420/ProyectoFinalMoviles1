package com.example.ProyectoFinal.api.servicios

import com.example.ProyectoFinal.models.DataRegistroUser
import com.example.ProyectoFinal.models.DataRespuestaRegistro
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistroInterfaz {


    @POST("/api/registrar")
    fun registrar(@Body dataRegistroUser: DataRegistroUser): Call<DataRespuestaRegistro>

}