package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {

    lateinit var LoginButton: Button
    lateinit var UserEmail: EditText
    lateinit var UserPassword: EditText
    lateinit var NeedNewAccountLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        NeedNewAccountLink = findViewById(R.id.register_account_link)
        UserEmail = findViewById(R.id.login_email)
        UserPassword = findViewById(R.id.login_password)
        LoginButton = findViewById(R.id.login_button)

        NeedNewAccountLink.setOnClickListener {
            sendUserToRegisterActivity()
        }
    }

    private fun sendUserToRegisterActivity() {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
    }
}