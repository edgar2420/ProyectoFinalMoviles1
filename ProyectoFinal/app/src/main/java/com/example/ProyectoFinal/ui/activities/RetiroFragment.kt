package com.example.ProyectoFinal.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ProyectoFinal.R
import com.example.ProyectoFinal.api.repositories.CuentasRepository
import com.example.ProyectoFinal.api.servicios.CuentasInterface
import com.example.ProyectoFinal.databinding.FragmentRetiroBinding
import com.example.ProyectoFinal.models.DataCuentaUser
import com.example.ProyectoFinal.models.DataMovimiento
import com.example.ProyectoFinal.ui.interfaces.RetiroIngresoLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetiroFragment : Fragment(), RetiroIngresoLoaded {

    private lateinit var binding: FragmentRetiroBinding
    private lateinit var mySpinner: Spinner
    private lateinit var adapter: ArrayAdapter<DataCuentaUser>
    private var listaCuentaUser: ArrayList<DataCuentaUser>? = null
    private var accessToken: String = ""
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

        binding = FragmentRetiroBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(acces_token: String) =
            RetiroFragment().apply {
                arguments = Bundle().apply {
                    accessToken = acces_token
                }
            }
    }

    fun fetchRealizarRetiro() {

        val monto = binding.txtMonto.text!!.toString().toIntOrNull()

        var detalle = binding.txtDetalle.text.toString()
        CuentasRepository.retiroDineroCuenta(this, accessToken, idCuenta, monto, detalle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mySpinner = binding.spinnerAccounts


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

        binding.btnRetirar.setOnClickListener {
            verificarCampos()
            fetchRealizarRetiro()
        }


    }

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
        // Obtener el monto ingresado por el usuario desde el campo de texto
        val monto = binding.txtMonto.text.toString().toIntOrNull()
        // Verificar si tanto la respuesta del movimiento como el monto son nulos
        if (dataMovimiento == null && monto == null) {
            println("llene los campos")
            return
        }
        // Verificar si el monto no es nulo y si el saldo en la cuenta es menor al monto a retirar

        if (monto != null && montoCuenta < monto!!) {
            // Imprime un mensaje indicando que el saldo en la cuenta es menor al monto a retirar
            println("el saldo en cuenta es menor que lo que desea retirar")
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