package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class h_cnsmrprofeditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.h_cnsmrprofedit)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        val hguardarButton : Button = findViewById(R.id.h_guardar)

        hguardarButton.setOnClickListener {
            // Cambiar a la actividad de i_cnsmrdirectoryActivity
            val intent = Intent(this, i_cnsmrdirectoryActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de i_cnsmrdirectoryActivity
        }

    }
}