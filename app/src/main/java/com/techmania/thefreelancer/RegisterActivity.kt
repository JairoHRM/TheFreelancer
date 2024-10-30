package com.techmania.thefreelancer

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var userConfirmPassword: EditText
    private lateinit var createAccountButton: Button
    private lateinit var loadingBar: ProgressDialog

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        userEmail = findViewById(R.id.register_email)
        userPassword = findViewById(R.id.register_password)
        userConfirmPassword = findViewById(R.id.register_confirm_password)
        createAccountButton = findViewById(R.id.register_create_account)
        loadingBar = ProgressDialog(this)

        createAccountButton.setOnClickListener {
            CreateNewAccount()
        }

    }

    private fun CreateNewAccount() {

        val email = userEmail.text.toString()
        val password = userPassword.text.toString()
        val confirmPassword = userConfirmPassword.text.toString()

        when {
            email.isEmpty() -> {
                Toast.makeText(this, "Por favor escriba su correo electrónico...", Toast.LENGTH_SHORT).show()
            }
            password.isEmpty() -> {
                Toast.makeText(this, "Por favor escriba su contraseña...", Toast.LENGTH_SHORT).show()
            }
            confirmPassword.isEmpty() -> {
                Toast.makeText(this, "Por favor confirme su contraseña...", Toast.LENGTH_SHORT).show()
            }
            password != confirmPassword -> {
                Toast.makeText(this, "Tus contraseñas no coinciden...", Toast.LENGTH_SHORT).show()
            }
            else -> {
                loadingBar.setTitle("Creando cuenta")
                loadingBar.setMessage("Por favor espere, esto puede tardar unos segundos...")
                loadingBar.setCanceledOnTouchOutside(true)
                loadingBar.show()

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sendUserToSetupActivity()

                        Toast.makeText(this, "Has creado tu cuenta correctamente...", Toast.LENGTH_SHORT).show()
                        loadingBar.dismiss()
                    }
                    else {
                        val message = task.exception?.message
                        Toast.makeText(this, "Error al crear la cuenta: $message", Toast.LENGTH_SHORT).show()
                        loadingBar.dismiss()
                    }

            }            }

    }
}

    private fun sendUserToSetupActivity() {
        val setupIntent = Intent(this, SetupActivity::class.java)
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(setupIntent)
        finish()
    }
}