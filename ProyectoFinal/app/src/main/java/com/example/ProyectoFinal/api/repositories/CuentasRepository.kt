package com.example.ProyectoFinal.api.repositories

import com.example.ProyectoFinal.api.servicios.CuentasInterface
import com.example.ProyectoFinal.models.DataCuentaUser
import com.example.ProyectoFinal.models.DataIngresoEgreso
import com.example.ProyectoFinal.models.DataMovimiento
import com.example.ProyectoFinal.ui.interfaces.CuentasLoaded
import com.example.ProyectoFinal.ui.interfaces.RetiroIngresoLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CuentasRepository {
    // Llamamos a la Api
    private val retrofitClient: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://apibancomoviles1.jmacboy.com")
        .build()
    // Método para obtener las cuentas del usuario
    fun getCuentasUser(listener: CuentasLoaded, access_token: String) {
        // Creamos la instancia de la interfaz
        val cuentaUser = retrofitClient.create(CuentasInterface::class.java)
        // Haciendo una solicitud GET al servidor para obtener las cuentas del usuario
        cuentaUser.getCuentas("Bearer " + access_token)
            .enqueue(object : Callback<ArrayList<DataCuentaUser>> {
                override fun onResponse(
                    call: Call<ArrayList<DataCuentaUser>>,
                    response: Response<ArrayList<DataCuentaUser>>
                ) {
                    val listaCuentasUser = response?.body()

                    listaCuentasUser.let {
                        // Notificando al listener que se han cargado las cuentas del usuario
                        listener.onLoginLoaded(it)
                    }
                }

                override fun onFailure(call: Call<ArrayList<DataCuentaUser>>, t: Throwable) {
                    listener.onErrorLoading(t, "Error cargando la lista de cuentas")
                }

            })
    }
    // Método para agregar una cuenta al usuario
    fun addCuentaUser(listener: CuentasLoaded, access_token: String) {
        val cuentaUser = retrofitClient.create(CuentasInterface::class.java)
        // Haciendo una solicitud POST al servidor para agregar una cuenta al usuario
        cuentaUser.addCuenta("Bearer " + access_token).enqueue(object : Callback<DataCuentaUser> {
            override fun onResponse(
                call: Call<DataCuentaUser>,
                response: Response<DataCuentaUser>
            ) {
                // Obteniendo la respuesta del servidor
                val response = response.body()
            }

            override fun onFailure(call: Call<DataCuentaUser>, t: Throwable) {
                listener.onErrorLoading(t, "Error cargando la lista de cuentas")
            }

        })
    }
    // Método para realizar un retiro de dinero de una cuenta
    fun retiroDineroCuenta(
        listener: RetiroIngresoLoaded,
        access_token: String, id: Int, monto: Int?, detalle: String ){
        // Haciendo una solicitud POST al servidor para realizar un retiro de dinero de una cuenta
        val retiroCuentaUser = retrofitClient.create((CuentasInterface::class.java))
        retiroCuentaUser.retirar(id, "Bearer " + access_token, DataIngresoEgreso(detalle, monto))
            .enqueue(object : Callback<DataMovimiento>{
                override fun onResponse(
                    call: Call<DataMovimiento>,
                    response: Response<DataMovimiento>
                ) {

                    val dataMovimiento = response?.body()

                    dataMovimiento.let {
                        // Notificando al listener que se ha realizado el retiro de dinero
                        listener.onRetiroIngresoLoaded(it)
                    }

                }

                override fun onFailure(call: Call<DataMovimiento>, t: Throwable) {
                    listener.onErrorLoading(t, "Error al realizar el retiro")
                }

            })
    }
    // Método para realizar un ingreso de dinero a una cuenta
    fun ingresoDineroCuenta(
        listener: RetiroIngresoLoaded,
        access_token: String, id: Int, monto: Int?, detalle: String ){
        // Haciendo una solicitud POST al servidor para realizar un ingreso de dinero a una cuenta
        val retiroCuentaUser = retrofitClient.create((CuentasInterface::class.java))
        retiroCuentaUser.ingresar(id, "Bearer " + access_token, DataIngresoEgreso(detalle, monto))
            .enqueue(object : Callback<DataMovimiento>{
                override fun onResponse(
                    call: Call<DataMovimiento>,
                    response: Response<DataMovimiento>
                ) {

                    val dataMovimiento = response?.body()

                    dataMovimiento.let {
                        // Notificando al listener que se ha realizado el ingreso de dinero
                        listener.onRetiroIngresoLoaded(it)
                    }

                }

                override fun onFailure(call: Call<DataMovimiento>, t: Throwable) {
                    listener.onErrorLoading(t, "Error al realizar el retiro")
                }

            })
    }

    // Método para obtener una instancia de Retrofit
    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://apibancomoviles1.jmacboy.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}