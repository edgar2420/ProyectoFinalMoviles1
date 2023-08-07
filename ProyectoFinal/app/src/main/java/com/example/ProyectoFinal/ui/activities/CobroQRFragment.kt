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
import com.example.ProyectoFinal.R
import com.example.ProyectoFinal.api.repositories.CuentasRepository
import com.example.ProyectoFinal.api.repositories.QRRepository
import com.example.ProyectoFinal.api.servicios.CuentasInterface
import com.example.ProyectoFinal.databinding.FragmentCobroQRBinding
import com.example.ProyectoFinal.models.DataCuentaUser
import com.example.ProyectoFinal.models.DataRespuestaCobroQR
import com.example.ProyectoFinal.ui.interfaces.CobroQRLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CobroQRFragment : Fragment(), CobroQRLoaded {

    private lateinit var binding: FragmentCobroQRBinding
    private lateinit var mySpinner: Spinner
    private var accessToken: String = ""
    private var codes: MutableList<String> = ArrayList()
    private var idCuenta: Int = 0
    private var imagen: String = ""
    private var listaCuentaUser: ArrayList<DataCuentaUser>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar la vista del fragmento a partir del archivo de diseño FragmentCobroQRBinding
        binding = FragmentCobroQRBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        // Método estático para crear una nueva instancia del fragmento con un token de acceso
        fun newInstance(acces_Token: String) =
            // Crear un nuevo Bundle para los argumentos del fragmento
            CobroQRFragment().apply {
                arguments = Bundle().apply {
                    accessToken = acces_Token
                    println(accessToken)
                }
            }
    }
    // Método para generar el QR
    fun fetchGenerarQR(){
        // obtengo el monto ingresado del campo de texto y convertirlo a Int
        var monto = binding.txtMontoQR.text.toString().toIntOrNull()
        // Obtengo  la fecha límite ingresada del campo de texto
        var fecha = binding.txtFechaLimiteQR.text.toString()
        println(idCuenta)
        // Llamamos al método en el repositorio para generar la imagen del QR
        QRRepository.generarImgQR(this,accessToken, monto, idCuenta, fecha)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mySpinner = binding.spinnerCuentasQR
        // Realizar una llamada a la API para obtener las cuentas del usuario
        val call = CuentasRepository.getRetrofit().create(CuentasInterface::class.java)
            .getCuentas("Bearer " + accessToken)
        call.enqueue(object : Callback<ArrayList<DataCuentaUser>> {
            override fun onResponse(
                call: Call<ArrayList<DataCuentaUser>>,
                response: Response<ArrayList<DataCuentaUser>>
            ) {
                listaCuentaUser = response.body()
                // Agregar los códigos de cuenta a la lista codes
                for (i in 0 until listaCuentaUser!!.size) {
                    codes.add(listaCuentaUser!!.get(i).numero)
                }
                // Creo un adapter de ArrayAdapter para el Spinner usando los códigos de cuenta
                val adapterTime = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_dropdown_item_1line,
                    codes
                )
                // asigno el adapter al Spinner
                mySpinner.adapter = adapterTime
                //configuro las acciones a realizar cuando se selecciona un elemento del Spinner
                mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {

                        // obtengo el id de cuenta seleccionado
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
        // configura el listener del botón "Generar QR"
        binding.btnGeneraQR.setOnClickListener {
            fetchGenerarQR()
        }
    }
    // Método para mostrar la imagen del QR
    override fun onCobroQRLoaded(dataRespuestaQR: DataRespuestaCobroQR?) {
        println(dataRespuestaQR)
        imagen = dataRespuestaQR!!.imagen

        replaceFragment(VisorImgQRFragment.newInstance(imagen))

    }
    // Método para mostrar un error en caso de que no se pueda generar el QR
    override fun onErrorLoading(error: Throwable?, message: String) {
        Log.e("ERROR", message, error)
    }
    // Método para reemplazar el fragmento actual por otro
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }
}