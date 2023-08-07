package com.example.ProyectoFinal.api.servicios

import com.example.ProyectoFinal.models.DataCuentaUser
import com.example.ProyectoFinal.models.DataIngresoEgreso
import com.example.ProyectoFinal.models.DataMovimiento
import retrofit2.Call
import retrofit2.http.*

interface CuentasInterface {

    @GET("/api/accounts")
    fun getCuentas(@Header("AUTHORIZATION") value: String): Call<ArrayList<DataCuentaUser>>

    @POST("/api/accounts")
    fun addCuenta(@Header("AUTHORIZATION") value: String): Call<DataCuentaUser>

    @POST("/api/accounts/{id}/expense")
    fun retirar(
        @Path("id") id: Int,
        @Header("AUTHORIZATION") value: String,
        @Body dataIngresoEgreso: DataIngresoEgreso): Call<DataMovimiento>

    @POST("/api/accounts/{id}/income")
    fun ingresar(
        @Path("id") id: Int,
        @Header("AUTHORIZATION") value: String,
        @Body dataIngresoEgreso: DataIngresoEgreso): Call<DataMovimiento>

}