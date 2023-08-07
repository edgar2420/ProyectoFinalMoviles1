package com.example.ProyectoFinal.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ProyectoFinal.api.repositories.RegistroRepository
import com.example.ProyectoFinal.databinding.ActivityRegistroUsuarioBinding
import com.example.ProyectoFinal.models.DataRespuestaRegistro
import com.example.ProyectoFinal.ui.interfaces.RegistroLoaded
import android.util.Patterns
import java.util.regex.Pattern


class RegistroUsuarioActivity : AppCompatActivity(), RegistroLoaded {

    private lateinit var binding: ActivityRegistroUsuarioBinding
    private var nombreCompleto: String = ""
    private var email: String = ""
    private var password: String = ""
    private var ci: String = ""
    private var fechaNacimiento: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupEventListeners()
    }

    fun fetchRegistrarUsuario() {
        nombreCompleto = binding.txtNombreCompletoUser.text.toString()
        email = binding.txtCorreoUser.text.toString()
        password = binding.txtPasswordUser.text.toString()
        ci = binding.txtCiUser.text.toString()
        fechaNacimiento = binding.txtFechaNacUser.text.toString()
        println(nombreCompleto + email + password + ci + fechaNacimiento)
        RegistroRepository.registrarUsuario(this, nombreCompleto, email, password, ci, fechaNacimiento)

    }

    fun setupEventListeners() {
        binding.btnRegistrarUsuario.setOnClickListener {
            validarCampos()
            fetchRegistrarUsuario()
        }
    }

    fun validarCampos() {
        if (nombreCompleto.isEmpty()) {
            binding.txtNombreCompletoUser.setError("Ingrese su nombre")
            return
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Ingrese su correo", Toast.LENGTH_SHORT).show()
            return
        }
        if (!validarEmail(email)) {
            binding.txtCorreoUser.setError("Ingrese un email válido")
            return
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show()
            return
        }
        if (ci.isEmpty()) {
            Toast.makeText(this, "Ingrese su Cédula de identidad", Toast.LENGTH_SHORT).show()
            return
        }
        if (fechaNacimiento.isEmpty()) {
            Toast.makeText(this, "Ingrese su Fecha de nacimiento", Toast.LENGTH_SHORT).show()
            return
        }

    }

    override fun onRegistroUsuarioLoaded(dataRespuestaRegistro: DataRespuestaRegistro?) {
        println(dataRespuestaRegistro)
        // Verificar si la respuesta del registro del usuario es nula
        if (dataRespuestaRegistro == null) {
            // Muesta el mensaje de verificar campos
            Toast.makeText(this, "Verifique los campos", Toast.LENGTH_SHORT).show()
            return
        }
        // Imprimir nuevamente la respuesta de registro del usuario en la consola
        println(dataRespuestaRegistro)
        // Mostrar mensaje de registro exitoso
        Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show()
        // Redireccionar a la pantalla de inicio de sesión
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    override fun onErrorLoading(error: Throwable?, message: String) {
        Log.e("ERROR", message, error)
    }
    //Metodo para validar el email
    private fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}