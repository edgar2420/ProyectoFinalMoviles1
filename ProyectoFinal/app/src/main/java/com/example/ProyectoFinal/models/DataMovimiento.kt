package com.example.ProyectoFinal.models

data class DataMovimiento(
    var descripcion: String,
    var monto: Int,
    var tipo: Int,
    var cuentaOrigen: Int,
    var cuentaDestino: Int,
    var created_at: String,
    var id: Int,
    var saldo: Int,
) {
    override fun toString(): String {
        return "DataMovimiento(descripcion='$descripcion', monto=$monto, tipo=$tipo, cuentaOrigen=$cuentaOrigen, cuentaDestino=$cuentaDestino, created_at='$created_at', id=$id, saldo=$saldo)"
    }
}