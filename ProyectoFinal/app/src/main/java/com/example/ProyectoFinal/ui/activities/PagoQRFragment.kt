package com.example.ProyectoFinal.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.ProyectoFinal.api.repositories.CuentasRepository
import com.example.ProyectoFinal.api.repositories.QRRepository
import com.example.ProyectoFinal.api.servicios.CuentasInterface
import com.example.ProyectoFinal.databinding.FragmentPagoQRBinding
import com.example.ProyectoFinal.models.DataCuentaUser
import com.example.ProyectoFinal.models.DataQRPagoRespuesta
import com.example.ProyectoFinal.ui.interfaces.PagoQRLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PagoQRFragment : Fragment(), PagoQRLoaded {

    private lateinit var binding: FragmentPagoQRBinding
    private lateinit var mySpinner: Spinner
    private var listaCuentaUser: ArrayList<DataCuentaUser>? = null
    private var accessToken: String = ""
    private var codes: MutableList<String> = ArrayList()
    private var idCuenta: Int = 0
    private var montoCuenta: Int = 0
    private var codigoQR: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPagoQRBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(access_Token: String,codigo: String) =
            PagoQRFragment().apply {
                arguments = Bundle().apply {
                    codigoQR = codigo
                    accessToken = access_Token
                }
            }
    }

    fun fetchpagoQR(){
        QRRepository.pagarQR(this, accessToken, codigoQR, idCuenta)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mySpinner = binding.spinnerCuentasQRPago


        val call = CuentasRepository.getRetrofit().create(CuentasInterface::class.java)
            .getCuentas("Bearer " + accessToken)
        call.enqueue(object : Callback<ArrayList<DataCuentaUser>> {
            override fun onResponse(
                call: Call<ArrayList<DataCuentaUser>>,
                response: Response<ArrayList<DataCuentaUser>>
            ) {
                listaCuentaUser = response.body()
                for (i in 0 until listaCuentaUser!!.size) {
                    codes.add(listaCuentaUser!!.get(i).numero)
                }
                val adapterTime = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_dropdown_item_1line,
                    codes
                )

                mySpinner.adapter = adapterTime
                mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        idCuenta = listaCuentaUser!!.get(p2).id
                        montoCuenta = listaCuentaUser!!.get(p2).saldo.toInt()


                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        //no hace nada
                    }

                }
            }

            override fun onFailure(call: Call<ArrayList<DataCuentaUser>>, t: Throwable) {
                Log.e("Error", "Error cargando las cuentas", t)
            }

        })

        binding.btnPagar.setOnClickListener {

            fetchpagoQR()
        }
    }

    override fun onPagoQRLoaded(dataQRPagoRespuesta: DataQRPagoRespuesta?) {
//        if(montoCuenta)
    }

    override fun onErrorLoading(error: Throwable?, message: String) {
        TODO("Not yet implemented")
    }
}