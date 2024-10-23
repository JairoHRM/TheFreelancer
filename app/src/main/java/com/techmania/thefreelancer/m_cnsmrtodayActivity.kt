package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class m_cnsmrtodayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.m_cnsmrtoday)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val magregarneedButton: Button = findViewById(R.id.m_agregarneed)

        magregarneedButton.setOnClickListener {
            // Cambiar a la actividad de h_cnsmrprofeditActivity
            val intent = Intent(this, h_cnsmrprofeditActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de h_cnsmrprofeditActivity
        }

        val mbuscarfrlncrButton: Button = findViewById(R.id.m_buscarfrlncrs)

        mbuscarfrlncrButton.setOnClickListener {
            // Cambiar a la actividad de n_cnsmrbuscarfrlncrsActivity
            val intent = Intent(this, n_cnsmrbuscarfrlncrsActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de n_cnsmrbuscarfrlncrsActivity
        }

        val mvermssgsButton: Button = findViewById(R.id.m_vermessgs)

        mvermssgsButton.setOnClickListener {
            // Cambiar a la actividad de o_cnsmrmssgsActivity
            val intent = Intent(this, o_cnsmrmssgsActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de o_cnsmrmssgsActivity
        }

    }
}