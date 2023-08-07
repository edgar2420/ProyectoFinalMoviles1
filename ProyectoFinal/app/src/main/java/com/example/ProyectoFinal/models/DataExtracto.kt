package com.example.ProyectoFinal.models

class DataExtracto(
    var id: Int,
    var descripcion: String,
    var monto: Double,
    var tipo: Int,
    var cuentaOrigen: Int,
    var cuentaDestino: Int,
    var created_at: String
) {
}