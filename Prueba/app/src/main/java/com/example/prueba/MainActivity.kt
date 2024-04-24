package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager.OnActivityDestroyListener
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.DragStartHelper.OnDragStartListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.prueba.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindig: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityMainBinding.inflate(layoutInflater)
        val view = bindig.root
        setContentView(view)
        bindig.btnIrActividad.setOnClickListener {
            iraSegundaAcatividad()
        }
        bindig.btnSaludar.setOnClickListener {
            val nombre = bindig.etCampoTBase.text.toString()
            if (esValidoT(nombre))
                mostrarNombre(nombre)
            else
                bindig.etCampoTBase.setError(getString(R.string.mensaje_error))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("Ciclo", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Ciclo", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Ciclo", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Ciclo", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Ciclo", "onDestroy")
    }
    fun iraSegundaAcatividad(){
        val intent = Intent(this@MainActivity, SeundaPantalla::class.java)
        val nombre = bindig.etCampoTBase.text.toString()
        intent.putExtra("saludo", nombre)
        startActivity(intent)

    }
    fun esValidoT(nombre: String):Boolean{
        return  nombre.isNotEmpty()
    }
    fun  mostrarNombre(nombre: String){
        Toast.makeText(this@MainActivity,"Hola " + nombre , Toast.LENGTH_LONG).show()
    }
}