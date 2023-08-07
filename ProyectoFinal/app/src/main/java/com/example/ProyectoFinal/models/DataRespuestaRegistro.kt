package com.example.ProyectoFinal.models

data class DataRespuestaRegistro(
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var perfil: DataPerfil? = null
) {
    override fun toString(): String {
        return "(id=$id, name='$name', email='$email', dataPerfil=$perfil)"
    }
}