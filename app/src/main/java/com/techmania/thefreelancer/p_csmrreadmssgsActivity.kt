package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class p_csmrreadmssgsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.p_csmrreadmssgs)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pdescartarButton: Button = findViewById(R.id.p_descartarmss)

        pdescartarButton.setOnClickListener {
            // Cambiar a la actividad de o_cnsmrmssgsActivity
            val intent = Intent(this, o_cnsmrmssgsActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de o_cnsmrmssgsActivity
        }

        val pcontestarButton: Button = findViewById(R.id.p_contestar)

        pcontestarButton.setOnClickListener {
            // Cambiar a la actividad de g_frlncrcommentActivity
            val intent = Intent(this, g_frlncrcommentActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de g_frlncrcommentActivity
        }

    }
}