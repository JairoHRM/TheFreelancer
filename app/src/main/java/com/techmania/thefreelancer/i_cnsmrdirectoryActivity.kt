package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class i_cnsmrdirectoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.i_cnsmrdirectory)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val iinicioButton : Button = findViewById(R.id.i_inicio)

        iinicioButton.setOnClickListener {
            // Cambiar a la actividad de MainActivity_a_entrada
            val intent = Intent(this, MainActivity_a_entrada::class.java)
            startActivity(intent)  // Iniciar la actividad de MainActivity_a_entrada
        }

    }
}