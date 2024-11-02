package com.techmania.thefreelancer

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Locale

class PostActivity : AppCompatActivity() {

    private lateinit var mToolbar: Toolbar

    private lateinit var selectPostImage: ImageButton
    private lateinit var updatePostButton: Button
    private lateinit var postDescription: EditText

    private val GALLERY_PICK = 1
    private var imageUri: Uri? = null
    private var description: String = ""

    private lateinit var postImagesReference: StorageReference

    private lateinit var saveCurrentDate: String
    private lateinit var saveCurrentTime: String
    private lateinit var postRandomName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post)

        postImagesReference = FirebaseStorage.getInstance().reference.child("Post Images")

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

        updatePostButton.setOnClickListener {
            validatePostInfo()
        }

        }

    private fun validatePostInfo() {
        description = postDescription.text.toString()

        when {
            imageUri == null -> {
                Toast.makeText(this, "Por favor selecciona una imagen para el post...", Toast.LENGTH_LONG).show()
            }
            description.isEmpty() -> {
                Toast.makeText(this, "Por favor di algo sobre tu imagen...", Toast.LENGTH_LONG).show()
            }
            else -> {
                //loadingBar.setTitle("Agregar Nuevo Post")
                //loadingBar.setMessage("Por favor espera, estamos actualizando tu nuevo post...")
                //loadingBar.show()
                //loadingBar.setCanceledOnTouchOutside(true)

                storingImageToFirebaseStorage()
            }
        }
    }

    private fun storingImageToFirebaseStorage() {
        val calForDate = Calendar.getInstance()
        val currentDate = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        saveCurrentDate = currentDate.format(calForDate.time)

        val calForTime = Calendar.getInstance()
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault())
        saveCurrentTime = currentTime.format(calForDate.time)

        postRandomName = saveCurrentDate + saveCurrentTime

        val filePath = postImagesReference.child("Imágenes de Post").child("${imageUri?.lastPathSegment}$postRandomName.jpg")

        filePath.putFile(imageUri!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //downloadUrl = task.result?.storage?.downloadUrl.toString()
                Toast.makeText(this, "Imagen subida correctamente al almacenamiento...", Toast.LENGTH_LONG).show()
                //savingPostInformationToDatabase()
            } else {
                val message = task.exception?.message
                Toast.makeText(this, "Ocurrió un error: $message", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun OpenGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        startActivityForResult(galleryIntent, GALLERY_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            selectPostImage.setImageURI(imageUri)
        }
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