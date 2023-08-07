package com.example.ProyectoFinal.api.servicios


import com.example.ProyectoFinal.models.DataLogin
import com.example.ProyectoFinal.models.Token
import retrofit2.Call
import retrofit2.http.*

interface LoginInterface {

//    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/api/login")
    fun login(@Body dataLogin: DataLogin): Call<Token>

}