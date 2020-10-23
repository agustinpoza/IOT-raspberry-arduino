package com.proyecto.hdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val database = Firebase.database

    var refCasa = database.getReference("home")
   lateinit var refLuz : DatabaseReference
   lateinit var refLuzCocina : DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myRef = database.getReference("message")
        val mensaje2 = myRef.child("message")

        mensaje2.setValue("prender luces")

        refLuz = refCasa.child("luces")
        refLuzCocina = refLuz.child("luz_cocina")

        botonPut.setOnClickListener {
            refLuzCocina.setValue("True")
        }

    }
}