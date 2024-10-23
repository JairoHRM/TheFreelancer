package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class b_registroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.b_registro)

        val frlncrButton: Button = findViewById(R.id.b_editproffrlncr)

        frlncrButton.setOnClickListener {
            // Cambiar a la actividad de c_frlncrprofedit
            val intent = Intent(this, c_frlncrprofeditActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de frlncrprofedit
        }

        val cnsmrButton: Button = findViewById(R.id.b_editprofcnsmr)

        cnsmrButton.setOnClickListener {
            // Cambiar a la actividad de h_cnsmrprofedit
            val intent = Intent(this, h_cnsmrprofeditActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de cnsmrprofedit
        }

    }

}