package com.example.prueba

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.prueba.databinding.ActivitySeundaPantallaBinding


class SeundaPantalla : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var bindig: ActivitySeundaPantallaBinding
    var nombre: String? = ""
    var tipoIntent: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivitySeundaPantallaBinding.inflate(layoutInflater)
        val view = bindig.root
        setContentView(view)
        nombre = intent.getStringExtra("saludo")
        agregarSaludo()
        bindig.btnAcercaDe.setOnClickListener {
            abrirDialogoAcercaDe()
        }
        bindig.btnVerWeb.setOnClickListener {
            if (validar())
                abrirSitioWeb(tipoIntent)
            else
                bindig.etUrl.setError(getString(R.string.mensaje_error))
        }

        //configurarSpinnerRecurso()
        configurarSpinnerArreglo()
        bindig.opcionesIntent.onItemSelectedListener = this


    }

    fun validar(): Boolean {
        return bindig.etUrl.text.isNotEmpty()
    }

    @SuppressLint("SetTextI18n")
    fun agregarSaludo() {
        bindig.tvBienvenida.text = "Bienvenido ${nombre}"
    }

    fun abrirSitioWeb(tipo : Int) {

        var url = bindig.etUrl.text.toString()

            when (tipo) {
                0 -> if(validarUrl(url)==true ){
                    url = "https://" + url
                } else{
                    Toast.makeText(this,"Valor invalido", Toast.LENGTH_SHORT).show()
                }
                1 -> if(validarTelefono(url)==true){
                    url = "tel:" + url
                } else {
                    Toast.makeText(this,"Valor invalido", Toast.LENGTH_SHORT).show()
                }
                2 -> if(validarEmail(url)){
                    url = "mailto:" + url
                } else {
                    Toast.makeText(this,"Valor invalido", Toast.LENGTH_SHORT).show()
                }
            }



        val uriConten = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uriConten)
        startActivity(intent)
    }

    fun abrirDialogoAcercaDe() {
        val aertaAcercaDe = AlertDialog.Builder(this@SeundaPantalla)
        aertaAcercaDe.setTitle("Acerca De")
        aertaAcercaDe.setMessage(
            "Bienvenido" +
                    "SkaJplr"
        )
        aertaAcercaDe.setPositiveButton("Aceptar", { dialogInterface, i ->
            Toast.makeText(this@SeundaPantalla, "Gracias", Toast.LENGTH_LONG).show()
        })
        aertaAcercaDe.setNegativeButton("Cancellar", null)
        aertaAcercaDe.setCancelable(false)
        val dialogo = aertaAcercaDe.create()
        dialogo.show()
    }

    //Llenar un Spinner con recursos utilizados, en esta coso de un xml
    fun configurarSpinnerRecurso() {
        ArrayAdapter.createFromResource(
            this@SeundaPantalla,
            R.array.opciones_intent,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bindig.opcionesIntent.adapter = it
        }

    }

    //Llenar un Spinner con una variable local
    fun configurarSpinnerArreglo() {
        val opcionesIntent = arrayOf("Sitio Web", "Llamda telefonica", "Envio de correo")
        val adapterArray = ArrayAdapter<String>(
            this@SeundaPantalla,
            android.R.layout.simple_spinner_dropdown_item,
            opcionesIntent
        )
        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bindig.opcionesIntent.adapter = adapterArray
    }


    //Seleccionar el Spinner
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.i("Seleccion intent:", "Posicion seleccionada: ${p2}")
        cargarOpcionesIntent(p2)
        tipoIntent = p2
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun cargarOpcionesIntent(opcion: Int){
        bindig.etUrl.setText("")

        when(opcion){
            0 -> {
                bindig.etUrl.hint = "Ingresa la URL del sitio web"
                bindig.btnVerWeb.text = "Ir al sitio web"
            }
            1 -> {
                bindig.etUrl.hint = "Ingresa el numero de telefono"
                bindig.btnVerWeb.text ="Realizar llamada"
            }
            2 -> {
                bindig.etUrl.hint = "Ingresa la direccion de correo"
                bindig.btnVerWeb.text ="Enviar correo"
            }
            else -> {
                bindig.btnVerWeb.text ="Sin opcion"
            }
        }
    }

    private fun validarUrl(url: String): Boolean {
        val regex = Regex("^https?://\\S+\$|^\\S+\$") // Expresión regular para validar una URL opcionalmente con "https://"
        return regex.matches(url)
    }


    private fun validarTelefono(telefono: String): Boolean {
        val regex = Regex("^[0-9]{10}\$")
        return regex.matches(telefono)
    }

    private fun validarEmail(email: String): Boolean {
        val regex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})") // Expresión regular para validar correo electrónico básico
        return regex.matches(email)
    }



}