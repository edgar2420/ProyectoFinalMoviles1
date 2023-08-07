package com.example.ProyectoFinal.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.ProyectoFinal.R
import com.example.ProyectoFinal.databinding.ActivityUserSesionBinding

class UserSesionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSesionBinding
    private lateinit var toogle: ActionBarDrawerToggle
    private var accessToken: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toogle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open_drawer, R.string.close_drawer)
        binding.drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        accessToken = intent.getStringExtra("access_token").toString()
        println(accessToken)

        replaceFragment(AccountsHomeFragment.newInstance(accessToken))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction().apply {
                        replaceFragment(AccountsHomeFragment.newInstance(accessToken))
                    }
                }

                R.id.nav_retiro -> {
                    supportFragmentManager.beginTransaction().apply {
                        replaceFragment(RetiroFragment.newInstance(accessToken))
                    }
                }

                R.id.nav_Ingreso -> {
                    supportFragmentManager.beginTransaction().apply {
                        replaceFragment(IngresoFragment.newInstance(accessToken))
                    }
                }

                R.id.nav_transferencia -> {
                    supportFragmentManager.beginTransaction().apply {
                        replaceFragment(TransferenciaFragment.newInstance(accessToken))
                    }
                }

                R.id.nav_CobroQR -> {
                    supportFragmentManager.beginTransaction().apply {
                        replaceFragment(CobroQRFragment.newInstance(accessToken))
                    }
                }

                R.id.nav_PagoQR -> {
                    supportFragmentManager.beginTransaction().apply {
                        replaceFragment(LectorQRFragment.newInstance(accessToken))
                    }
                }

                R.id.nav_CerrarSesion -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)

            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toogle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

}