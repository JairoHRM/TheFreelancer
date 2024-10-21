package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class a_entradaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_entrada)

        val registroButton: Button = findViewById(R.id.registro)

        registroButton.setOnClickListener {
            // Cambiar a la actividad de registro
            val intent = Intent(this, b_registroActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de registro
        }
    }
}