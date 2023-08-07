package com.example.ProyectoFinal.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.example.ProyectoFinal.R
import com.example.ProyectoFinal.databinding.FragmentLectorQRBinding
import java.util.*

class LectorQRFragment : Fragment() {

    private lateinit var binding: FragmentLectorQRBinding
    private var id: String = ""
    private var accessToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScanner()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLectorQRBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(acces_Token: String) =
            LectorQRFragment().apply {
                arguments = Bundle().apply {
                    accessToken = acces_Token
                }
            }
    }

    private fun initScanner(){
        val integrator = IntentIntegrator(requireActivity())
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(activity, "Cancelado", Toast.LENGTH_LONG).show()
            } else {
                id=result.contents.toString()
                Toast.makeText(activity, "El valor escaneado es: " + result.contents, Toast.LENGTH_LONG).show()
                replaceFragment(PagoQRFragment.newInstance(accessToken, id))
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)

        }
    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }


    private fun startScanning() {
//        myScanner = binding.scannerView
//        codeScanner = CodeScanner(requireActivity(), scannerView)
    }
}