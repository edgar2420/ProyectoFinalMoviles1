package com.example.ProyectoFinal.api.repositories

import com.example.ProyectoFinal.api.servicios.LoginInterface
import com.example.ProyectoFinal.models.DataLogin
import com.example.ProyectoFinal.models.Token
import com.example.ProyectoFinal.ui.interfaces.LoginLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginRepository {
    // Creamos una instancia de Retrofit para realizar las solicitudes a la API
    private val retrofitClient: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://apibancomoviles1.jmacboy.com")
        .build()
    // Método para realizar el inicio de sesión de un usuario
    fun loginUser(listener: LoginLoaded, email: String, password: String) {
        // Haciendo una solicitud POST al servidor para iniciar sesión con el email y contraseña proporcionados
        val loginUser = retrofitClient.create(LoginInterface::class.java)
        loginUser.login(DataLogin(email, password))
            .enqueue(object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    val login_token = response?.body()

                    login_token.let {
                        // Notificando al listener que se ha realizado el inicio de sesión
                            listener.onLoginLoaded(it)
                    }
                }
                override fun onFailure(call: Call<Token>, t: Throwable) {
                    listener.onErrorLoading(t, "Datos incorrectos")
                }

            })
    }
}