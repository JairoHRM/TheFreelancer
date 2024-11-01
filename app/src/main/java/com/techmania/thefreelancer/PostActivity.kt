package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PostActivity : AppCompatActivity() {

    private lateinit var mToolbar: Toolbar

    private lateinit var selectPostImage: ImageButton
    private lateinit var updatePostButton: Button
    private lateinit var postDescription: EditText

    private val GALLERY_PICK = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post)

        selectPostImage = findViewById(R.id.select_post_image)
        updatePostButton = findViewById(R.id.update_post_button)
        postDescription = findViewById(R.id.post_description)

        mToolbar = findViewById(R.id.update_post_page_toolbar)
        setSupportActionBar(mToolbar)
        getSupportActionBar()?.setTitle("Postear")
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        selectPostImage.setOnClickListener {
            OpenGallery()
        }

        }

    private fun OpenGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        startActivityForResult(galleryIntent, GALLERY_PICK)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.getItemId()

        if (id == android.R.id.home) {
            sendUserToMainActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun sendUserToMainActivity() {
        val mainIntent = Intent(this@PostActivity, MainActivity::class.java)
        startActivity(mainIntent)

    }

}