package com.example.ProyectoFinal.api.repositories

import com.example.ProyectoFinal.api.servicios.QRInterfaz
import com.example.ProyectoFinal.models.DataCobroQR
import com.example.ProyectoFinal.models.DataPagoQR
import com.example.ProyectoFinal.models.DataQRPagoRespuesta
import com.example.ProyectoFinal.models.DataRespuestaCobroQR
import com.example.ProyectoFinal.ui.interfaces.CobroQRLoaded
import com.example.ProyectoFinal.ui.interfaces.PagoQRLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QRRepository {
    // Creamos una instancia de Retrofit para realizar las solicitudes a la API
    private val retrofitClient: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://apibancomoviles1.jmacboy.com")
        .build()
    // Método para generar una imagen de código QR para el cobro
    fun generarImgQR(listener: CobroQRLoaded, acces_Token: String, monto: Int?, cuentaDestino: Int, fechaLimite: String){
        val cobroQr = retrofitClient.create(QRInterfaz::class.java)
        // Haciendo una solicitud POST al servidor para generar el código QR de cobro
        cobroQr.generarQR("Bearer "+ acces_Token, DataCobroQR(monto, cuentaDestino, fechaLimite))
            .enqueue(object : Callback<DataRespuestaCobroQR>{
                override fun onResponse(
                    call: Call<DataRespuestaCobroQR>,
                    response: Response<DataRespuestaCobroQR>
                ) {
                    val respuestaCobroQR = response?.body()

                    respuestaCobroQR.let {
                        listener.onCobroQRLoaded(it)
                    }
                }

                override fun onFailure(call: Call<DataRespuestaCobroQR>, t: Throwable) {
                    listener.onErrorLoading(t, "Error al generar el QR")
                }

            })
    }
    // Método para pagar un código QR
    fun pagarQR(listener: PagoQRLoaded,acces_Token: String, codigo: String, cuentaOrigen: Int){
        val pagoQr= retrofitClient.create(QRInterfaz::class.java)
        pagoQr.pagoQR("Bearer " + acces_Token, DataPagoQR(codigo, cuentaOrigen))
            .enqueue(object  : Callback<DataQRPagoRespuesta>{
                override fun onResponse(
                    call: Call<DataQRPagoRespuesta>,
                    response: Response<DataQRPagoRespuesta>,
                ) {
                    val pagoQRresponse = response?.body()
                    pagoQRresponse.let {
                        listener.onPagoQRLoaded(it)
                    }
                }

                override fun onFailure(call: Call<DataQRPagoRespuesta>, t: Throwable) {
                    listener.onErrorLoading(t, "Error al pagar el qr")
                }

            })
    }
}