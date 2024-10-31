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
            val destinationUri = Uri.fromFile(File(cacheDir, "cropped"))

            UCrop.of(imageUri!!, destinationUri)
                .withAspectRatio(1f, 1f)
                .withMaxResultSize(450, 450)
                .start(this)
        }

        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            if (resultUri != null) {
                uploadProfileImage(resultUri)
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Toast.makeText(this, "Error: Image cropping failed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadProfileImage(uri: Uri) {
        loadingBar.setTitle("Profile Image")
        loadingBar.setMessage("Please wait while we upload your profile image...")
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
                usersRef.child("profileimage").setValue(downloadUrl).addOnCompleteListener { setValueTask ->
                    if (setValueTask.isSuccessful) {
                        Toast.makeText(this, "Profile Image successfully stored.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error storing image in database.", Toast.LENGTH_SHORT).show()
                    }
                    loadingBar.dismiss()
                }
            } else {
                Toast.makeText(this, "Image upload failed.", Toast.LENGTH_SHORT).show()
                loadingBar.dismiss()
            }
        }
    }

    private fun saveAccountSetupInformation() {
        val username = userName.text.toString()
        val fullname = fullName.text.toString()

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(fullname)) {
            Toast.makeText(this, "Please enter your username and full name.", Toast.LENGTH_SHORT).show()
            return
        }

        loadingBar.setTitle("Saving Account Information")
        loadingBar.setMessage("Please wait while we save your information.")
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
                Toast.makeText(this, "Account setup complete.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Error saving account information.", Toast.LENGTH_SHORT).show()
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
