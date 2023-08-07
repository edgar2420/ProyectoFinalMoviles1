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
import com.example.ProyectoFinal.api.repositories.CuentasRepository
import com.example.ProyectoFinal.api.servicios.CuentasInterface
import com.example.ProyectoFinal.databinding.FragmentIngresoBinding
import com.example.ProyectoFinal.models.DataCuentaUser
import com.example.ProyectoFinal.models.DataMovimiento
import com.example.ProyectoFinal.ui.interfaces.RetiroIngresoLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresoFragment : Fragment(), RetiroIngresoLoaded{

    private lateinit var binding: FragmentIngresoBinding
    private lateinit var mySpinner: Spinner
    private var listaCuentaUser: ArrayList<DataCuentaUser>? = null
    private var accessToken: String = ""
    private var codes: MutableList<String> = ArrayList()
    private var idCuenta: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIngresoBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun fetchRealizarIngreso() {

        val monto = binding.txtMonto.text!!.toString().toIntOrNull()

        var detalle = binding.txtDetalle.text.toString()
        CuentasRepository.ingresoDineroCuenta(this, accessToken, idCuenta, monto, detalle)
    }


    companion object {

        @JvmStatic
        fun newInstance(acces_token: String) =
            IngresoFragment().apply {
                arguments = Bundle().apply {
                    accessToken = acces_token
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mySpinner = binding.spinnerAccounts

        // Obtener la lista de cuentas del usuario
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
        // Botón para realizar el ingreso de dinero
        binding.btnIngresarDinero.setOnClickListener {
            verificarCampos()
            fetchRealizarIngreso()
        }


    }
    // Método para verificar los campos de monto y detalle
    fun verificarCampos() {
        var monto = binding.txtMonto.text
        var detalle = binding.txtDetalle.text
        if (monto.isEmpty()) {
            Toast.makeText(activity, "Ingrese un monto", Toast.LENGTH_SHORT).show()
            return
        }
        if (detalle.isEmpty()) {
            Toast.makeText(activity, "Ingrese un detalle", Toast.LENGTH_SHORT).show()
            return
        }
    }

    override fun onRetiroIngresoLoaded(dataMovimiento: DataMovimiento?) {
        if (dataMovimiento == null) {
            println("llene los campos")
            return
        }
        Toast.makeText(activity, "Transaccion exitosa", Toast.LENGTH_SHORT).show()
        println(dataMovimiento)
        replaceFragment(AccountsHomeFragment.newInstance(accessToken))
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