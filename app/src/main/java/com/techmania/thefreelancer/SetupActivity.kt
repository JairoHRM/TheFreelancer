package com.techmania.thefreelancer

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView

class SetupActivity : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var fullName: EditText
    private lateinit var saveInformationButton: Button
    private lateinit var profileImage: CircleImageView
    private lateinit var loadingBar: ProgressDialog

    private lateinit var mAuth: FirebaseAuth
    private lateinit var usersRef: DatabaseReference

    private lateinit var currentUserID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setup)

        mAuth = FirebaseAuth.getInstance()
        currentUserID = mAuth.currentUser!!.uid
        usersRef = FirebaseDatabase.getInstance().reference.child("Usuarios")

        userName = findViewById(R.id.setup_username)
        fullName = findViewById(R.id.setup_full_name)
        saveInformationButton = findViewById(R.id.setup_information_button)
        profileImage = findViewById(R.id.setup_profile_image)
        loadingBar = ProgressDialog(this)

        saveInformationButton.setOnClickListener {
            saveAccountSetupInformation()
        }
    }

    private fun saveAccountSetupInformation() {
        val username = userName.text.toString()
        val fullname = fullName.text.toString()

        when {
            TextUtils.isEmpty(username) -> Toast.makeText(
                this,
                "Por favor escriba su usuario...",
                Toast.LENGTH_SHORT
            ).show()

            TextUtils.isEmpty(fullname) -> Toast.makeText(
                this,
                "Por favor escriba su nombre completo...",
                Toast.LENGTH_SHORT
            ).show()

            else -> {
                loadingBar.setTitle("Guardando información de la cuenta...")
                loadingBar.setMessage("Por favor espere, esto puede tardar unos segundos...")
                loadingBar.show()
                loadingBar.setCanceledOnTouchOutside(true)

                val userMap = HashMap<String, Any>()
                userMap["username"] = username
                userMap["fullname"] = fullname
                userMap["status"] = "Hola estoy usando The Freelancer!"
                userMap["setupCompleted"] = true

                usersRef.child(currentUserID).updateChildren(userMap)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            sendUserToMainActivity()
                            Toast.makeText(
                                this@SetupActivity, "Tu cuenta ha sido creada correctamente...",
                                Toast.LENGTH_LONG).show()
                            loadingBar.dismiss()
                        } else {
                            val message = task.exception?.message
                            Toast.makeText(
                                this@SetupActivity, "Error al crear la cuenta: $message",
                                Toast.LENGTH_SHORT).show()
                            loadingBar.dismiss()
                        }
                    }
            }
        }
    }

    private fun sendUserToMainActivity() {
        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
        finish()
    }
}