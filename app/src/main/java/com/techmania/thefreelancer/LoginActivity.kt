package com.techmania.thefreelancer

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var userEmail: EditText
    lateinit var userPassword: EditText
    lateinit var needNewAccountLink: TextView
    lateinit var loadingBar: ProgressDialog

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        needNewAccountLink = findViewById(R.id.register_account_link)
        userEmail = findViewById(R.id.login_email)
        userPassword = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        loadingBar = ProgressDialog(this)

        needNewAccountLink.setOnClickListener {
            sendUserToRegisterActivity()
        }

        loginButton.setOnClickListener {
            allowUserToLogin()
        }
    }

    private fun allowUserToLogin() {
        val email = userEmail.text.toString()
        val password = userPassword.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(
                this, "Por favor escriba su correo electrónico...",
                Toast.LENGTH_SHORT
            ).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Por favor escriba su contraseña...", Toast.LENGTH_SHORT)
                .show()
        } else {
            loadingBar.setTitle("Inicio de sesión")
            loadingBar.setMessage("Por favor espere, esto puede tardar unos segundos...")
            loadingBar.setCanceledOnTouchOutside(true)
            loadingBar.show()

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener() {
                if (it.isSuccessful) {
                    sendUserToMainActivity()

                    Toast.makeText(this, "Has iniciado sesión correctamente...", Toast.LENGTH_SHORT)
                        .show()
                    loadingBar.dismiss()
                } else {
                    val message = it.exception?.message
                    Toast.makeText(this, "Error al iniciar sesión: $message", Toast.LENGTH_SHORT)
                        .show()
                    loadingBar.dismiss()
                }
            }
        }
    }

    private fun sendUserToMainActivity() {
        var mainActivityIntent = Intent(this, MainActivity::class.java)
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainActivityIntent)
        finish()
    }


    private fun sendUserToRegisterActivity() {
        val registerIntent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)
    }
}
