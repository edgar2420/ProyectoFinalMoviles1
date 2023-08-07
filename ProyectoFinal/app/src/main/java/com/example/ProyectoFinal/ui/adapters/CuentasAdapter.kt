package com.example.ProyectoFinal.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ProyectoFinal.models.DataCuentaUser
import com.example.ProyectoFinal.databinding.ItemCuentaBinding

class CuentasAdapter(
    var listaCuentas: ArrayList<DataCuentaUser>,
    var listener: onCuentaClickListener
) : RecyclerView.Adapter<CuentasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCuentaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val objCuenta = listaCuentas[position]
        holder.binding.lblNroCuenta.text = objCuenta.numero
        holder.binding.lblSaldoCuenta.text = objCuenta.saldo.toString() + "Bs"
        holder.binding.cardViewCuenta.setOnClickListener{
            listener.onClicked(objCuenta)
        }
    }

    override fun getItemCount(): Int {
        return listaCuentas.size
    }

    fun setData(arrCuentas: ArrayList<DataCuentaUser>?){
        this.listaCuentas = arrCuentas!!
        this.notifyDataSetChanged()
    }


    interface onCuentaClickListener {
        fun onClicked(dataCuentaUser: DataCuentaUser?)
    }

    class ViewHolder(val binding: ItemCuentaBinding) :
        RecyclerView.ViewHolder(binding.root)
}

