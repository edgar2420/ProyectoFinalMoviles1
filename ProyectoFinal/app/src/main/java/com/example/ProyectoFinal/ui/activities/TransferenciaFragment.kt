package com.example.ProyectoFinal.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ProyectoFinal.R
import com.example.ProyectoFinal.api.repositories.BeneficiarioRepository
import com.example.ProyectoFinal.databinding.FragmentTransferenciaBinding
import com.example.ProyectoFinal.models.DataBeneficiariosList
import com.example.ProyectoFinal.ui.adapters.BeneficiarioAdapter
import com.example.ProyectoFinal.ui.interfaces.BeneficiarioLoaded


class TransferenciaFragment : Fragment(), BeneficiarioAdapter.onBeneficiarioClickListener, BeneficiarioLoaded {

    private var accessToken: String = ""
    private lateinit var binding: FragmentTransferenciaBinding
    private lateinit var adapter: BeneficiarioAdapter
    private var dataBeneficiariosList: DataBeneficiariosList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchBeneficiariosUser()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTransferenciaBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun fetchBeneficiariosUser(){
        BeneficiarioRepository.getListaBeneficiarios(this, accessToken)
    }

    companion object {

        @JvmStatic
        fun newInstance(acces_Token: String) =
            TransferenciaFragment().apply {
                arguments = Bundle().apply {
                    accessToken = acces_Token
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        binding.btnAddBeneficiario.setOnClickListener {
            replaceFragment(RegistroBeneficiarioFragment.newInstance(accessToken))
        }

    }

    fun setupRecyclerView(){
        binding.lstBeneficiario.setHasFixedSize(true)
        adapter = BeneficiarioAdapter(arrayListOf(), this)
        val linearLayoutV = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.lstBeneficiario.layoutManager = linearLayoutV
        val dividerItemDecoration = DividerItemDecoration(
            binding.lstBeneficiario.context,
            linearLayoutV.orientation
        )
        binding.lstBeneficiario.addItemDecoration(dividerItemDecoration)
        //adapter!!.setData(listaCuentasUser)
        binding.lstBeneficiario.adapter = adapter
    }

    override fun onClicked(dataBeneficiario: DataBeneficiariosList) {
        replaceFragment(TransferenciaBeneficiarioFragment.newInstance(accessToken, dataBeneficiario?.id, dataBeneficiario.nombreCompleto))
    }

    override fun onBeneficiarioLoaded(dataBeneficiario: ArrayList<DataBeneficiariosList>?) {

        adapter.setData(dataBeneficiario)
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