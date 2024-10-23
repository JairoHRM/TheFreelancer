package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class s_frlncrreadmssgsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.s_frlncrreadmssgs)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sdescartarButton: Button = findViewById(R.id.s_descartar)

        sdescartarButton.setOnClickListener {
            // Cambiar a la actividad de r_frlncrmssgsreceivedActivity
            val intent = Intent(this, r_frlncrmssgsreceivedActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de r_frlncrmssgsreceivedActivity
        }

        val scontestarButton: Button = findViewById(R.id.s_contestar)

        scontestarButton.setOnClickListener {
            // Cambiar a la actividad de k_cnsmrmessageActivity
            val intent = Intent(this, k_cnsmrmessageActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de k_cnsmrmessageActivity
        }

    }
}