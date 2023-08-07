package com.example.ProyectoFinal.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ProyectoFinal.api.repositories.LoginRepository
import com.example.ProyectoFinal.databinding.ActivityMainBinding
import com.example.ProyectoFinal.models.Token
import com.example.ProyectoFinal.ui.interfaces.LoginLoaded

class MainActivity : AppCompatActivity(), LoginLoaded {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupEventListeners()


    }
    // Método para obtener el token de acceso
    fun fetchLoging() {
        var proyecto = binding.textView.text.toString()
        var email = binding.txtEmail.text.toString()
        var password = binding.txtContrasena.text.toString()
        LoginRepository.loginUser(this,email,password)
    }

    private fun setupEventListeners() {
        binding.btnRegistrar.setOnClickListener {
            val intent = Intent(this, RegistroUsuarioActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        binding.btnIngresar.setOnClickListener {
            validarInicio()
            fetchLoging()

        }
    }



    private fun validarInicio() {
        val email = binding.txtEmail.text.toString()
        if (email.isEmpty()) {
            Toast.makeText(this, "Debe ingresar su correo", Toast.LENGTH_SHORT).show()
            return
        }

        val contrasena = binding.txtContrasena.text.toString()
        if (contrasena.isEmpty()) {
            Toast.makeText(this, "Debe ingresar su contraseña", Toast.LENGTH_SHORT).show()
            return
        }
contentResolver

    }

    override fun onLoginLoaded(token: Token?) {

        if (token == null) {
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, UserSesionActivity::class.java)
        intent.putExtra("access_token",token.access_token)
        startActivity(intent)
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
        binding.txtEmail.setText("")
        binding.txtContrasena.setText("")
        this.finish()


    }

    override fun onErrorLoading(error: Throwable?, message: String) {

        Log.e("ERROR", message, error)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}