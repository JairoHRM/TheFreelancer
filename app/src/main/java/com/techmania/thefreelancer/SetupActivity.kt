package com.techmania.thefreelancer

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.yalantis.ucrop.UCrop
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class SetupActivity : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var fullName: EditText
    private lateinit var saveInformationButton: Button
    private lateinit var profileImage: CircleImageView
    private lateinit var loadingBar: ProgressDialog

    private lateinit var mAuth: FirebaseAuth
    private lateinit var usersRef: DatabaseReference
    private lateinit var userProfileImageRef: StorageReference

    private lateinit var currentUserID: String
    private val GALLERY_PICK = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        mAuth = FirebaseAuth.getInstance()
        currentUserID = mAuth.currentUser?.uid ?: ""
        usersRef = FirebaseDatabase.getInstance().reference.child("Users").child(currentUserID)
        userProfileImageRef = FirebaseStorage.getInstance().reference.child("Profile Images")

        userName = findViewById(R.id.setup_username)
        fullName = findViewById(R.id.setup_full_name)
        saveInformationButton = findViewById(R.id.setup_information_button)
        profileImage = findViewById(R.id.setup_profile_image)
        loadingBar = ProgressDialog(this)

        saveInformationButton.setOnClickListener {
            saveAccountSetupInformation()
        }

        profileImage.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
            startActivityForResult(galleryIntent, GALLERY_PICK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            if (imageUri != null) {
                try {
                    val destinationUri = Uri.fromFile(File(cacheDir, "cropped"))

                    // Start cropping with UCrop
                    UCrop.of(imageUri, destinationUri)
                        .withAspectRatio(1f, 1f)
                        .withMaxResultSize(450, 450)
                        .start(this)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "No se puede cargar la imagen", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "No se puede tomar la imagen", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            data?.let {
                val resultUri = UCrop.getOutput(it)
                if (resultUri != null) {
                    uploadProfileImage(resultUri)
                } else {
                    Toast.makeText(this, "No se puede recortar la imagen", Toast.LENGTH_LONG).show()
                }
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == UCrop.RESULT_ERROR) {
            data?.let {
                val cropError = UCrop.getError(it)
                cropError?.let { error ->
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                    error.printStackTrace()
                } ?: run {
                    Toast.makeText(this, "Error desconocido en el recorte de la imagen", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun uploadProfileImage(uri: Uri) {
        loadingBar.setTitle("Imagen de perfil")
        loadingBar.setMessage("Por favor espere mientras cargamos la imagen...")
        loadingBar.show()

        val filePath = userProfileImageRef.child("$currentUserID.jpg")
        filePath.putFile(uri).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            filePath.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUrl = task.result.toString()
                usersRef.child("Imagen de perfil").setValue(downloadUrl).addOnCompleteListener { setValueTask ->
                    if (setValueTask.isSuccessful) {
                        Toast.makeText(this, "Imagen de perfil guardada.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Error en el almacenamiento de la imagen.", Toast.LENGTH_LONG).show()
                    }
                    loadingBar.dismiss()
                }
            } else {
                Toast.makeText(this, "No se pudo cargar la imagen.", Toast.LENGTH_LONG).show()
                loadingBar.dismiss()
            }
        }
    }

    private fun saveAccountSetupInformation() {
        val username = userName.text.toString()
        val fullname = fullName.text.toString()

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(fullname)) {
            Toast.makeText(this, "Por favor entrar su usuario y nombre completo.", Toast.LENGTH_LONG).show()
            return
        }

        loadingBar.setTitle("Información de cuenta")
        loadingBar.setMessage("Por favor espere mientras guardamos la información.")
        loadingBar.show()

        val userMap: Map<String, Any> = mapOf(
            "username" to username,
            "fullname" to fullname,
            "status" to "Hola estoy usando The Freelancer!",
            "setupCompleted" to true
        )

        usersRef.updateChildren(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sendUserToMainActivity()
                Toast.makeText(this, "Autenticación de cuenta completa.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Error guardando la información.", Toast.LENGTH_LONG).show()
            }
            loadingBar.dismiss()
        }
    }

    private fun sendUserToMainActivity() {
        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
        finish()
    }
}
