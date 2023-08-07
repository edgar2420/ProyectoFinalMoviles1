package com.example.ProyectoFinal.models

class DataBeneficiariosList(

    var numeroCuenta: String,
    var ci: String,
    var nombreCompleto: String,
    var userId: Int,
    var updated_at: String,
    var created_at: String,
    var id: Int
) {
    override fun toString(): String {
        return "DataBeneficiariosList(id=$id, numeroCuenta='$numeroCuenta', ci=$ci, nombreCompleto='$nombreCompleto', userId=$userId, created_at='$created_at', updated_at='$updated_at')"
    }
}