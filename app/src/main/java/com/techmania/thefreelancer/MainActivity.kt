package com.techmania.thefreelancer

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
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


class MainActivity : AppCompatActivity() {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var postList: RecyclerView
    lateinit var mToolbar: Toolbar
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        mToolbar = findViewById(R.id.main_page_toolbar)
        setSupportActionBar(mToolbar)
        getSupportActionBar()?.setTitle("Inicio")

        navigationView = findViewById(R.id.navigation_view)
        drawerLayout = findViewById(R.id.drawable_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navView: View = navigationView.inflateHeaderView(R.layout.navigation_header)

        navigationView.setNavigationItemSelectedListener { item ->
            UserMenuSelector(item)
            false
        }

    }

    override fun onStart() {
        super.onStart()

        val currentUser: FirebaseUser? = mAuth.currentUser

        if (currentUser == null) {
            sendUserToLoginActivity()
        }
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