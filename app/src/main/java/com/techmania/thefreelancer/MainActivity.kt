package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var postList: RecyclerView
    private lateinit var mToolbar: Toolbar

    private lateinit var navProfileImage: CircleImageView
    private lateinit var navProfileUserName: TextView
    private lateinit var addNewPostButton: ImageButton

    private lateinit var mAuth: FirebaseAuth
    private lateinit var usersRef: DatabaseReference

    private lateinit var currentUserID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        currentUserID = mAuth.getCurrentUser()?.getUid() ?: ""
        usersRef = FirebaseDatabase.getInstance().reference.child("Users")

        mToolbar = findViewById(R.id.main_page_toolbar)
        setSupportActionBar(mToolbar)
        getSupportActionBar()?.setTitle("Inicio")

        addNewPostButton = findViewById(R.id.add_new_post_button)

        navigationView = findViewById(R.id.navigation_view)
        drawerLayout = findViewById(R.id.drawable_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navView: View = navigationView.inflateHeaderView(R.layout.navigation_header)
        navProfileImage = navView.findViewById(R.id.nav_profile_image)
        navProfileUserName = navView.findViewById(R.id.nav_user_full_name)

        usersRef.child(currentUserID).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.child("fullname").getValue(String::class.java)?.let { fullname ->
                        navProfileUserName.text = fullname
                    }

                    if (dataSnapshot.hasChild("profileimage")) {
                        dataSnapshot.child("profileimage").getValue(String::class.java)?.let { image ->
                            Picasso.get().load(image).placeholder(R.drawable.profile).into(navProfileImage)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Nombre del perfil no existe...", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })

        navigationView.setNavigationItemSelectedListener { item ->
            UserMenuSelector(item)
            false
        }

        navigationView.setNavigationItemSelectedListener { item ->
            UserMenuSelector(item)
            false
        }

        addNewPostButton.setOnClickListener {
            sendUserToPostActivity()
        }

    }

    private fun sendUserToPostActivity() {
        val addNewPostIntent = Intent(this@MainActivity, PostActivity::class.java)
        startActivity(addNewPostIntent)
    }

    override fun onStart() {
        super.onStart()

        val currentUser: FirebaseUser? = mAuth.currentUser

        if (currentUser == null) {
            sendUserToLoginActivity()
        }
        else {
            checkUserExistence()
        }
    }

    private fun checkUserExistence() {
        val current_user_id = mAuth.currentUser?.uid ?: ""
        // Target the specific user's node in the database
        usersRef.child(current_user_id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Check if the user node exists and if setupCompleted is true
                val setupCompleted = dataSnapshot.child("setupCompleted").getValue(Boolean::class.java)
                Log.d("MainActivity", "Setup Completed: $setupCompleted")
                if (setupCompleted != true) {
                    sendUserToSetupActivity()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error if needed
            }
        })
    }

    /*private fun checkUserExistence() {
        val current_user_id = mAuth.currentUser?.uid ?: ""
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists() || dataSnapshot.child("setupCompleted").getValue(Boolean::class.java) != true) {
                    // If user node doesn't exist or setup is not completed, go to SetupActivity
                    sendUserToSetupActivity()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }*/

    private fun sendUserToSetupActivity() {
        val setupIntent = Intent(this@MainActivity, SetupActivity::class.java)
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(setupIntent)
        finish()
    }

    private fun sendUserToLoginActivity() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(loginIntent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }

    private fun UserMenuSelector(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_post -> {
                sendUserToPostActivity()
            }

            R.id.nav_profile -> {
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_home -> {
                Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_freelancers -> {
                Toast.makeText(this, "Freelancers", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_find_freelancers -> {
                Toast.makeText(this, "Encontrar Freelancers", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_messages -> {
                Toast.makeText(this, "Mensajes", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_settings -> {
                Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_logout -> {
                mAuth.signOut()
                sendUserToLoginActivity()
            }
        }
    }
}