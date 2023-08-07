package com.example.ProyectoFinal.api.repositories

import com.example.ProyectoFinal.api.servicios.BeneficiarioInterfaz
import com.example.ProyectoFinal.models.DataBeneficiario
import com.example.ProyectoFinal.models.DataBeneficiariosList
import com.example.ProyectoFinal.models.DataRespuestaTransferencia
import com.example.ProyectoFinal.models.DataTransferencia
import com.example.ProyectoFinal.ui.interfaces.BeneficiarioCreatedLoaded
import com.example.ProyectoFinal.ui.interfaces.BeneficiarioLoaded
import com.example.ProyectoFinal.ui.interfaces.TransferenciasLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BeneficiarioRepository {
    private val retrofitClient: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://apibancomoviles1.jmacboy.com")
        .build()
    // Método para insertar un nuevo beneficiario
    fun insertarBeneficiario(
        listener: BeneficiarioCreatedLoaded,
        acces_token: String,
        nroCuenta: String,
        ci: String,
        nombreCompleto: String
    ) {
        val registroBeneficiario = retrofitClient.create(BeneficiarioInterfaz::class.java)
        // Haciendo una solicitud POST al servidor para registrar un beneficiario
        registroBeneficiario.registrarBeneficiario(
            "Bearer " + acces_token,
            DataBeneficiario(nroCuenta, ci, nombreCompleto)
        ).enqueue(object : Callback<DataBeneficiariosList> {
            override fun onResponse(
                call: Call<DataBeneficiariosList>,
                response: Response<DataBeneficiariosList>
            ) {
                val response = response.body()
                response.let {
                    // Notificando al listener que se ha creado el beneficiario
                    listener.onBeneficiarioCreatedLoaded(it)
                }
            }

            override fun onFailure(call: Call<DataBeneficiariosList>, t: Throwable) {
                listener.onErrorLoading(t, "Error cargando al registrar beneficiario")
            }

        })

    }
    // Método para obtener la lista de beneficiarios
    fun getListaBeneficiarios(listener: BeneficiarioLoaded, acces_token: String) {
        val getBeneficiarios = retrofitClient.create(BeneficiarioInterfaz::class.java)
        // Haciendo una solicitud GET al servidor para obtener la lista de beneficiarios
        getBeneficiarios.getBeneficiarios("Bearer " + acces_token)
            .enqueue(object : Callback<ArrayList<DataBeneficiariosList>> {
                override fun onResponse(
                    call: retrofit2.Call<ArrayList<DataBeneficiariosList>>,
                    // Notificando al listener que se ha cargado la lista de beneficiarios
                    response: Response<ArrayList<DataBeneficiariosList>>
                ) {
                    val listaBeneficiarios = response?.body()
                    listaBeneficiarios.let {
                        listener.onBeneficiarioLoaded(it)
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<DataBeneficiariosList>>,
                    t: Throwable
                ) {
                    listener.onErrorLoading(t, "Error cargando la lista de beneficiarios")
                }

            })
    }
    // Método para realizar una transferencia a un beneficiario
    fun realizarTransferenciaBeneficiario(
        listener: TransferenciasLoaded,
        acces_token: String,
        idBeneficiario: Int,
        idCuenta: Int,
        monto: Int?,
        descripcion: String
    ) {
        val transferirDineroBeneficiario = retrofitClient.create((BeneficiarioInterfaz::class.java))
        // Haciendo una solicitud POST al servidor para realizar una transferencia a un beneficiario
        transferirDineroBeneficiario.realizarTransferencia("Bearer "+ acces_token, DataTransferencia(idBeneficiario, idCuenta,monto,descripcion))
            .enqueue(object : Callback<DataRespuestaTransferencia>{
                override fun onResponse(
                    // Notificando al listener que se ha realizado la transferencia
                    call: Call<DataRespuestaTransferencia>,
                    response: Response<DataRespuestaTransferencia>
                ) {
                    // Obteniendo la respuesta del servidor
                    val datosMovimiento = response?.body()
                    datosMovimiento.let {
                        listener.onTransferenciaLoading(it)
                    }
                }
                // Notificando al listener que ha ocurrido un error al realizar la transferencia
                override fun onFailure(call: Call<DataRespuestaTransferencia>, t: Throwable) {
                    listener.onErrorLoading(t, "Error al realizar la transferencia")
                }

            }
        )

    }

}