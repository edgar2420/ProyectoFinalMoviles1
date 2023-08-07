package com.example.ProyectoFinal.models

class DataRespuestaCobroQR(
    var codigo: String,
    var monto: Int?,
    var cuentaDestino: Int,
    var fechaLimite: String,
    var created_at: String,
    var id: Int,
    var imagen: String
) {
    override fun toString(): String {
        return "(codigo='$codigo', monto=$monto, cuentaDestino=$cuentaDestino, fechaLimite='$fechaLimite', created_at='$created_at', id=$id, imagen='$imagen')"
    }
}