package com.techmania.thefreelancer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    lateinit var toolbar : MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.`Entrada.xml`)

        toolbar = findViewById(R.id.toolbar)

        toolbar.overflowIcon = AppCompatResources.getDrawable(this,R.drawable.baseline_more_vert_24)

        toolbar.setNavigationOnClickListener {

            Toast.makeText(applicationContext,"Navigation icon is clicked", Toast.LENGTH_SHORT).show()

        }

        toolbar.setOnMenuItemClickListener { item ->

            when(item.itemId){

                R.id.back -> Toast.makeText(applicationContext,"Back icon is clicked", Toast.LENGTH_SHORT).show()
                R.id.edit -> Toast.makeText(applicationContext,"Edit icon is clicked", Toast.LENGTH_SHORT).show()
                R.id.settings -> Toast.makeText(applicationContext,"Settings icon is clicked", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(applicationContext,"Sign Out icon is clicked", Toast.LENGTH_SHORT).show()

            }

            return@setOnMenuItemClickListener true

            

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}