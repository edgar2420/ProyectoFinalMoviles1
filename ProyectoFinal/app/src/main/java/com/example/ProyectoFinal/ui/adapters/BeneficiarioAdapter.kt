package com.example.ProyectoFinal.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ProyectoFinal.databinding.ItemBeneficiarioBinding
import com.example.ProyectoFinal.models.DataBeneficiariosList

class BeneficiarioAdapter(
    var listaBeneficiarios: ArrayList<DataBeneficiariosList>,
    var listener: onBeneficiarioClickListener
) : RecyclerView.Adapter<BeneficiarioAdapter.ViewHolderBeneficiario>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBeneficiario {
        return BeneficiarioAdapter.ViewHolderBeneficiario(
            ItemBeneficiarioBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderBeneficiario, position: Int) {
        val objBeneficiario = listaBeneficiarios[position]
        holder.binding.lblNroCuentaBeneficiario.text = objBeneficiario.numeroCuenta
        holder.binding.nombreBeneficiario.text = objBeneficiario.nombreCompleto

        holder.binding.cardViewBeneficiario.setOnClickListener {
            listener.onClicked(objBeneficiario)
        }



    }

    override fun getItemCount(): Int {
        return listaBeneficiarios.size
    }

    fun setData(arrBeneficiarios: ArrayList<DataBeneficiariosList>?){
        this.listaBeneficiarios = arrBeneficiarios!!
        this.notifyDataSetChanged()
    }

    interface onBeneficiarioClickListener{
        fun onClicked(dataBeneficiario: DataBeneficiariosList)
    }

    class ViewHolderBeneficiario(val binding: ItemBeneficiarioBinding):
        RecyclerView.ViewHolder(binding.root)


}