package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class n_cnsmrbuscarfrlncrsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.n_cnsmrbuscarfrlncrs)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nbuscarButton: Button = findViewById(R.id.n_buscar)

        nbuscarButton.setOnClickListener {
            // Cambiar a la actividad de d_frlncrdirectoryActivity
            val intent = Intent(this, d_frlncrdirectoryActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de d_frlncrdirectoryActivity
        }

    }
}