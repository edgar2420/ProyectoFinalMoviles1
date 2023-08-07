package com.example.ProyectoFinal.models

data class DataPerfil(
    var id: Int? = null,
    var fechaNacimiento: String? = null,
    var ci: String? = null
) {
    override fun toString(): String {
        return "(id=$id, fechaNacimiento='$fechaNacimiento', ci='$ci')"
    }
}