package com.proyecto.hdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ToggleButton
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var database = Firebase.database

    var refCasa = database.getReference("Casa")

    //luces
    lateinit var refLuz: DatabaseReference
    lateinit var refLuzCocina: DatabaseReference

    //temperaturas
    lateinit var refTemp: DatabaseReference
    lateinit var refTempCocina: DatabaseReference

    //layout
    var btnToggle: ToggleButton? = null
    var textTemperatura: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //luces
        refLuz = refCasa.child("luces")
        refLuzCocina = refCasa.child("Luz")
        refLuzCocina.setValue(true)

        //temperaturas
       //refTemp = refCasa.child("Temperaturas")
        refTempCocina = refCasa.child("Temperatura")
        refTempCocina.setValue("0")

        //layout
        btnToggle = findViewById<ToggleButton>(R.id.toggleButton)
        textTemperatura = findViewById(R.id.textTemperatura)

        luzControl(refLuzCocina, btnToggle)
        tempControl(refTempCocina, textTemperatura)


    }

    private fun tempControl(refTempCocina: DatabaseReference, textTemperatura: TextView?) {
        refTempCocina.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val Temperatura = snapshot.value as String
                textTemperatura?.text = Temperatura
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }


    //creamos una funcion para controlar refLuzCocina
    private fun luzControl(refLuzCocina: DatabaseReference?, btnToggle: ToggleButton?) {
        //funcion para que el programa escuche cada cambio del boton y lo envie al database
        btnToggle?.setOnCheckedChangeListener { _, isChecked ->
            refLuzCocina?.setValue(
                isChecked
            )
        }
        // creamos un escucha para refLuzCocina
        refLuzCocina?.addValueEventListener(object : ValueEventListener {
            //funcion que se ejecuta cada vez que toma una datasnapshot
            override fun onDataChange(snapshot: DataSnapshot) {
                //creamos un valor al cual le asignamos el dato de nuestro snapshot
                val estadoLuz = snapshot.value as Boolean
                //hacemos que nuestro boton tome el estado del valor anteriormente asignado
                btnToggle?.isChecked = estadoLuz
                //se grafcara en el boton si esta encendido o apagado
                if (estadoLuz) {
                    toggleButton.textOn = "ENCENDIDO"
                } else {
                    toggleButton.textOff = "APAGADO"
                }
            }

            //error en el firebase
            override fun onCancelled(error: DatabaseError) {
                println("error Firebase refLuzCocina")
            }
        })

    }
}