package com.example.ProyectoFinal.api.repositories

import com.example.ProyectoFinal.api.servicios.ExtractosInterface
import com.example.ProyectoFinal.models.DataExtracto
import com.example.ProyectoFinal.ui.interfaces.ExtractosLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExtratoRepository {
    // Creamos una instancia de Retrofit para realizar las solicitudes a la API
    private val retrofitClient: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://apibancomoviles1.jmacboy.com")
        .build()

    // Método para obtener el extracto de una cuenta de usuario
    fun getExtractoCuentaUser(listener: ExtractosLoaded, idCuenta: Int, access_token: String){
        val extractosCuenta = retrofitClient.create((ExtractosInterface::class.java))
        // Haciendo una solicitud GET al servidor para obtener el extracto de la cuenta
        extractosCuenta.getExtracto(idCuenta, "Bearer " + access_token).enqueue(object : Callback<ArrayList<DataExtracto>>{
            override fun onResponse(
                call: Call<ArrayList<DataExtracto>>,
                response: Response<ArrayList<DataExtracto>>
            ) {
                val listaExtractosCuenta = response?.body()

                listaExtractosCuenta.let {
                    // Notificando al listener que se han cargado los extractos de la cuenta
                    listener.onExtractosLoaded(it)
                }
            }

            override fun onFailure(call: Call<ArrayList<DataExtracto>>, t: Throwable) {
                listener.onErrorLoading(t,"Error cargando los extractos")
            }

        })
    }
}