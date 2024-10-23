package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class o_cnsmrmssgsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.o_cnsmrmssgs)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val oinicioButton: Button = findViewById(R.id.o_inicio)

        oinicioButton.setOnClickListener {
            // Cambiar a la actividad de MainActivity_a_entrada
            val intent = Intent(this, MainActivity_a_entrada::class.java)
            startActivity(intent)  // Iniciar la actividad de MainActivity_a_entrada
        }

    }

}