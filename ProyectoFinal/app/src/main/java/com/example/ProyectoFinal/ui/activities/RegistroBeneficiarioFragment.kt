package com.example.ProyectoFinal.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ProyectoFinal.R
import com.example.ProyectoFinal.api.repositories.BeneficiarioRepository
import com.example.ProyectoFinal.databinding.FragmentRegistroBeneficiarioBinding
import com.example.ProyectoFinal.models.DataBeneficiariosList
import com.example.ProyectoFinal.ui.interfaces.BeneficiarioCreatedLoaded

class RegistroBeneficiarioFragment : Fragment(), BeneficiarioCreatedLoaded {

    private var accessToken: String = ""
    private lateinit var binding: FragmentRegistroBeneficiarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistroBeneficiarioBinding.inflate(inflater, container, false)
        return binding.root
    }



    companion object {
        @JvmStatic
        fun newInstance(acces_token: String) =
            RegistroBeneficiarioFragment().apply {
                arguments = Bundle().apply {

                    accessToken = acces_token
                    println("Acces-Token = " + accessToken)
                }
            }
    }
    fun fetchCrearBeneficiario() {

        var nroCuenta = binding.txtNroCuenta.text
        var ci = binding.txtCedulaIdentidad.text.toString()
        var nombreCompleto = binding.txtNombreCompleto.text
        println("-------------------"+accessToken)
        BeneficiarioRepository.insertarBeneficiario(
            this,
            accessToken,
            nroCuenta.toString(),
            ci,
            nombreCompleto.toString()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddBeneficiarioUser.setOnClickListener {
            //verificarCampos()
            fetchCrearBeneficiario()
        }
    }


    override fun onBeneficiarioCreatedLoaded(dataBeneficiario: DataBeneficiariosList?) {
        var nroCuenta = binding.txtNroCuenta.text
        var ci = binding.txtCedulaIdentidad.text
        var nombreCompleto = binding.txtNombreCompleto.text

        if (nroCuenta.isEmpty()) {
            Toast.makeText(activity, "Ingrese un Número de Cuenta", Toast.LENGTH_SHORT).show()
            return
        }
        if (ci.isEmpty()) {
            Toast.makeText(activity, "Ingrese una Cédula de Identidad", Toast.LENGTH_SHORT).show()
            return
        }
        if (nombreCompleto.isEmpty()) {
            Toast.makeText(activity, "Ingrese el nombre del Beneficiario", Toast.LENGTH_SHORT)
                .show()
            return
        }

        println(dataBeneficiario)

        Toast.makeText(activity, "Beneficiario Creado Exitosamente", Toast.LENGTH_SHORT).show()
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