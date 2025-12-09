package com.example.demociclovida

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var btnRestar : Button
    private lateinit var btnSumar : Button
    private lateinit var tv1 : TextView

    private var contador = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("Contador","Entramos en onCreate")


        btnRestar = findViewById(R.id.btn1)
        btnSumar = findViewById(R.id.btn2)
        tv1 = findViewById(R.id.tv1)

        Snackbar.make(btnSumar,"Has entrado en onCreate", Snackbar.LENGTH_LONG)
            .setAction("DESHACER"){
                // Accion al pulsar deshacer
                Log.d("Contador", "Has pulsado deshacer")
            }
            .show()


        //var contador = 0

        btnRestar.setOnClickListener {
            //contador--
            //tv1.text = contador.toString()
            tv1.text = (--contador).toString()
        }
        btnSumar.setOnClickListener {
            //contador++
            //tv1.text = contador.toString()
            tv1.text = (++contador).toString()
        }
    }
    // Fin del onCreate

    // Guardamos el valor del contador
    // Se ejecuta antes de onDestroy
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("Contador","Valor de contador: $contador")
        outState.putInt("CONTADOR",contador)
    }

    // Se ejecuta después de onStart (Antes de pasar a primer plano)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        contador = savedInstanceState.getInt("CONTADOR")
        Log.d("Contador", "Contador recuperado: $contador")
        tv1.text = contador.toString()
    }

    override fun onStart() {
        super.onStart()
        //setContentView(R.layout.activity_main)
        Log.d("Contador","Entramos en onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Contador","Entramos en onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Contador","Entramos en onRestart")

    }

    override fun onPause() {
        super.onPause()
        Log.d("Contador","Entramos en onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Contador","Entramos en onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Contador","Entramos en onDestroy")
    }

}