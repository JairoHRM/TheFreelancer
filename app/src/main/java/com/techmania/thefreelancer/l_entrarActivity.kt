package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class l_entrarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.l_entrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lcnsmrButton: Button = findViewById(R.id.l_consumer)

        lcnsmrButton.setOnClickListener {
            // Cambiar a la actividad de m_cnsmrtodayActivity
            val intent = Intent(this, m_cnsmrtodayActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de m_cnsmrtodayActivity
        }

        val lfrlncrButton: Button = findViewById(R.id.l_freelancer)

        lfrlncrButton.setOnClickListener {
            // Cambiar a la actividad de t_frlncrtodayActivity
            val intent = Intent(this, t_frlncrtodayActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de t_frlncrtodayActivity
        }

    }
}