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
import com.example.ProyectoFinal.api.repositories.CuentasRepository
import com.example.ProyectoFinal.databinding.FragmentAccountsHomeBinding
import com.example.ProyectoFinal.models.DataCuentaUser
import com.example.ProyectoFinal.ui.adapters.CuentasAdapter
import com.example.ProyectoFinal.ui.interfaces.CuentasLoaded


class AccountsHomeFragment : Fragment(), CuentasAdapter.onCuentaClickListener, CuentasLoaded {

    private lateinit var binding: FragmentAccountsHomeBinding
    private lateinit var adapter: CuentasAdapter
    private var accessToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchCuentaUser()
    }

    // Método para obtener las cuentas del usuario
    fun fetchCuentaUser() {
        CuentasRepository.getCuentasUser(this, accessToken)
    }

    // Método para agregar una cuenta al usuario
    fun fetchAddCuentaUser() {
        CuentasRepository.addCuentaUser(this, accessToken)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountsHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(access_token: String) =
            AccountsHomeFragment().apply {
                arguments = Bundle().apply {
                    accessToken = access_token
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Manejo del clic en el botón para agregar una cuenta
        binding.btnAddCuenta.setOnClickListener {
            fetchAddCuentaUser()
            fetchCuentaUser()
        }
    }

    // Configuración del RecyclerView
    fun setupRecyclerView() {
        binding.lstCuentas.setHasFixedSize(true)
        adapter = CuentasAdapter(arrayListOf(), this)
        val linearLayoutV = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.lstCuentas.layoutManager = linearLayoutV
        val dividerItemDecoration = DividerItemDecoration(
            binding.lstCuentas.context,
            linearLayoutV.orientation
        )
        binding.lstCuentas.addItemDecoration(dividerItemDecoration)
        binding.lstCuentas.adapter = adapter
    }

    // Método para manejar el click en una cuenta
    override fun onClicked(dataCuentaUser: DataCuentaUser?) {
        var fragment: Fragment
        fragment = ExtractosFragment.newInstance(dataCuentaUser?.id, accessToken)

        replaceFragment(fragment, false)
    }

    // Implementación del método de CuentasLoaded para manejar la respuesta de la obtención de cuentas del usuario
    override fun onLoginLoaded(cuentaUser: ArrayList<DataCuentaUser>?) {
        adapter.setData(cuentaUser)
    }

    override fun onErrorLoading(error: Throwable?, message: String) {
        Log.e("ERROR", message, error)
    }

    // Método para reemplazar un fragmento en el contenedor de fragmentos
    fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }
}