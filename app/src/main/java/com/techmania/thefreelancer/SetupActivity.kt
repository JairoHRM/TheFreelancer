package com.techmania.thefreelancer

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import de.hdodenhof.circleimageview.CircleImageView

class SetupActivity : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var fullName: EditText
    private lateinit var saveInformationButton: Button
    private lateinit var profileImage: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setup)

        userName = findViewById(R.id.setup_username)
        fullName = findViewById(R.id.setup_full_name)
        saveInformationButton = findViewById(R.id.setup_information_button)
        profileImage = findViewById(R.id.setup_profile_image)

    }
}