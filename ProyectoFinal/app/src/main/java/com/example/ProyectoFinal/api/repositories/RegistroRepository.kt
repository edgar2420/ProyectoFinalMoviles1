package com.example.ProyectoFinal.api.repositories

import com.example.ProyectoFinal.api.servicios.RegistroInterfaz
import com.example.ProyectoFinal.models.DataRegistroUser
import com.example.ProyectoFinal.models.DataRespuestaRegistro
import com.example.ProyectoFinal.ui.interfaces.RegistroLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RegistroRepository {
    // Creamos una instancia de Retrofit para realizar las solicitudes a la API
    private val retrofitClient: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://apibancomoviles1.jmacboy.com")
        .build()
    // Funcion pAra registrar el Usuario
    fun registrarUsuario(
        listener: RegistroLoaded,
        nombreCompleto: String,
        email: String,
        password: String,
        ci: String,
        fechaNacimiento: String,
    ) {
        val registroUser = retrofitClient.create(RegistroInterfaz::class.java)

        registroUser.registrar(DataRegistroUser(nombreCompleto, email, password, ci, fechaNacimiento))
            .enqueue(object : Callback<DataRespuestaRegistro> {
                override fun onResponse(
                    call: Call<DataRespuestaRegistro>, response: Response<DataRespuestaRegistro>,
                ) {


                    var respuestaRegistro = response?.body()
                    respuestaRegistro.let {
                        listener.onRegistroUsuarioLoaded(it)
                    }
                }

                override fun onFailure(call: Call<DataRespuestaRegistro>, t: Throwable) {
                    listener.onErrorLoading(t, "Error resgistrando el usuario")
                }

            })
    }
}