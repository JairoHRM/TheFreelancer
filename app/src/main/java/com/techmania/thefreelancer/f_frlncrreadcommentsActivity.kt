package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class f_frlncrreadcommentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.f_frlncrreadcomments)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val commssgsButton : Button = findViewById(R.id.f_commsmssgs)

        commssgsButton.setOnClickListener {
            // Cambiar a la actividad de g_frlncrcommentActivity
            val intent = Intent(this, g_frlncrcommentActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de g_frlncrcommentActivity
        }

    }
}