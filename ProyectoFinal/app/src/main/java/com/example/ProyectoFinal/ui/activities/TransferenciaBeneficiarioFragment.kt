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
import android.widget.Toast
import com.example.ProyectoFinal.R
import com.example.ProyectoFinal.api.repositories.BeneficiarioRepository
import com.example.ProyectoFinal.api.repositories.CuentasRepository
import com.example.ProyectoFinal.api.servicios.CuentasInterface
import com.example.ProyectoFinal.databinding.FragmentTransferenciaBeneficiarioBinding
import com.example.ProyectoFinal.models.DataCuentaUser
import com.example.ProyectoFinal.models.DataRespuestaTransferencia
import com.example.ProyectoFinal.ui.interfaces.TransferenciasLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransferenciaBeneficiarioFragment : Fragment(), TransferenciasLoaded {

    private lateinit var binding: FragmentTransferenciaBeneficiarioBinding
    private var accessToken: String = ""
    private var idBeneficiario: Int = 0
    private var nombreBeneficiario: String = ""
    private lateinit var mySpinner: Spinner
    private var listaCuentasUser: ArrayList<DataCuentaUser>? = null
    private var codes: MutableList<String> = ArrayList()
    private var idCuenta: Int = 0
    private var montoCuenta: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransferenciaBeneficiarioBinding.inflate(inflater, container,false)
        return binding.root
    }

    fun fetchTransferencia(){
        var monto = binding.txtMontoTransferencia.text.toString().toIntOrNull()
        var descripcion = binding.txtDescripcionTransferencia.text.toString()
        BeneficiarioRepository.realizarTransferenciaBeneficiario(this,accessToken, idBeneficiario, idCuenta, monto, descripcion)
    }

    companion object {

        @JvmStatic
        fun newInstance(acces_Token: String, id: Int?, nombre: String) =
            TransferenciaBeneficiarioFragment().apply {
                arguments = Bundle().apply {
                    accessToken = acces_Token
                    idBeneficiario = id!!
                    nombreBeneficiario = nombre
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mySpinner = binding.spinnerCuentas


        val call = CuentasRepository.getRetrofit().create(CuentasInterface::class.java)
            .getCuentas("Bearer " + accessToken)
        call.enqueue(object : Callback<ArrayList<DataCuentaUser>> {
            override fun onResponse(
                call: Call<ArrayList<DataCuentaUser>>,
                response: Response<ArrayList<DataCuentaUser>>
            ) {
                listaCuentasUser = response.body()
                for (i in 0 until listaCuentasUser!!.size) {
                    codes.add(listaCuentasUser!!.get(i).numero)
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
                        idCuenta = listaCuentasUser!!.get(p2).id
                        montoCuenta = listaCuentasUser!!.get(p2).saldo.toInt()
//                        Toast.makeText(activity, listaCuentaUser!!.get(p2).id.toString(), Toast.LENGTH_SHORT).show()

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

        binding.btnTransferir.setOnClickListener {
            fetchTransferencia()
        }
        binding.txtNombreBeneficiario.isEnabled = false
        binding.txtNombreBeneficiario.setText(nombreBeneficiario)

    }

    override fun onTransferenciaLoading(respuestaTransferencia: DataRespuestaTransferencia?) {
        var monto = binding.txtMontoTransferencia.text.toString().toIntOrNull()
        var descripcion = binding.txtDescripcionTransferencia.text
        println(idBeneficiario)
        println(idCuenta)
        println(respuestaTransferencia)
        if(monto == null){
            Toast.makeText(activity, "Ingrese el monto", Toast.LENGTH_SHORT).show()
            return
        }
        if(descripcion.isEmpty()){
            Toast.makeText(activity, "Ingrese la descripción", Toast.LENGTH_SHORT).show()
            return
        }

        if(monto != null && montoCuenta < monto!!){
            Toast.makeText(activity, "No tiene saldo suficiente en su Cuenta, por favor seleccione otra", Toast.LENGTH_SHORT).show()
            return
        }

        if(respuestaTransferencia == null){
            Toast.makeText(activity, "El beneficiario registrado no coincide con las cuentas", Toast.LENGTH_SHORT).show()
            return
        }
        

        Toast.makeText(activity, "Transferencia exitosa", Toast.LENGTH_SHORT).show()
        println(respuestaTransferencia)
        replaceFragment(TransferenciaFragment.newInstance(accessToken))

    }

    override fun onErrorLoading(error: Throwable?, message: String) {
        Log.e("ERROR", message, error)
    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }
}