package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class t_frlncrtodayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.t_frlncrtoday)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tmodifyprofButton: Button = findViewById(R.id.t_modifyprof)

        tmodifyprofButton.setOnClickListener {
            // Cambiar a la actividad de c_frlncrprofeditActivity
            val intent = Intent(this, c_frlncrprofeditActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de c_frlncrprofeditActivity
        }

        val tbuscarprojectsButton: Button = findViewById(R.id.t_buscarprojects)

        tbuscarprojectsButton.setOnClickListener {
            // Cambiar a la actividad de q_frlncrbuscarcnsmrsActivity
            val intent = Intent(this, q_frlncrbuscarcnsmrsActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de q_frlncrbuscarcnsmrsActivity
        }

        val tvermssgButton: Button = findViewById(R.id.t_vermessg)

        tvermssgButton.setOnClickListener {
            // Cambiar a la actividad de s_frlncrreadmssgsActivity
            val intent = Intent(this, s_frlncrreadmssgsActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de s_frlncrreadmssgsActivity
        }

    }
}