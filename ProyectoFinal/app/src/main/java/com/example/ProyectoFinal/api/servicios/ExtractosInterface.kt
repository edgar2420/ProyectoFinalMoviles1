package com.example.ProyectoFinal.api.servicios

import com.example.ProyectoFinal.models.DataExtracto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ExtractosInterface {

    @GET("/api/accounts/{id}/statement")
    fun getExtracto(@Path("id") id: Int,
    @Header("AUTHORIZATION") value: String): Call<ArrayList<DataExtracto>>

}