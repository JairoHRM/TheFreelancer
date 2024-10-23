package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class e_frlncrcommentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.e_frlncrcomments)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val commsmssgsButton: Button = findViewById(R.id.e_commmssg)

        commsmssgsButton.setOnClickListener {
            // Cambiar a la actividad de f_frlncrreadcommentsActivity
            val intent = Intent(this, f_frlncrreadcommentsActivity::class.java)
            startActivity(intent)  // Iniciar la actividad de f_frlncrreadcommentsActivity
        }

    }
}