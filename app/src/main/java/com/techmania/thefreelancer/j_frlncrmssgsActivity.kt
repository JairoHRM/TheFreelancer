package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class j_frlncrmssgsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.j_frlncrmssgs)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val jenviarmssgButton : Button = findViewById(R.id.j_enviarmensaje)

        jenviarmssgButton.setOnClickListener {
            // Cambiar a la actividad de k_cnsmrmessage
            val intent = Intent(this, k_cnsmrmessageActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de k_cnsmrmessageActivity
        }

    }
}