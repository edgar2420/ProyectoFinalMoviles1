package com.example.ProyectoFinal.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ProyectoFinal.models.DataExtracto
import com.example.ProyectoFinal.databinding.ItemExtractoBinding

class ExtractosAdapter(
    var listaExtractos: ArrayList<DataExtracto>
) : RecyclerView.Adapter<ExtractosAdapter.ViewHolderItemExtracto>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItemExtracto {
        return ViewHolderItemExtracto(
            ItemExtractoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderItemExtracto, position: Int) {
        val objExtracto = listaExtractos[position]
        val tipo = objExtracto.tipo

        if(tipo == 1){
            holder.binding.lblTipo.text = "Ingreso"
        } else if(tipo < 0){
            holder.binding.lblTipo.text = "Egreso"
        }

        holder.binding.lblDescripcion.text = objExtracto.descripcion
        holder.binding.lblMonto.text = objExtracto.monto.toString() + "Bs"

    }

    override fun getItemCount(): Int {
        return listaExtractos.size
    }

    fun setData(listaExtractos: ArrayList<DataExtracto>?){
        this.listaExtractos = listaExtractos!!
        this.notifyDataSetChanged()
    }

    class ViewHolderItemExtracto(val binding: ItemExtractoBinding) :
        RecyclerView.ViewHolder(binding.root)

}