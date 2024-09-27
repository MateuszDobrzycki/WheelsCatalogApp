package com.example.wheelscatalogapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var tvRedirectLogin: TextView
    lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    lateinit var btnLogin: Button
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        tvRedirectLogin = findViewById(R.id.tvRedirectLogin)
        btnLogin = findViewById(R.id.loginButton)
        etEmail = findViewById(R.id.loginEmailAddress)
        etPass = findViewById(R.id.loginPassword)
        auth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener {
            login()
        }
        tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun login() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Pomyślnie zalogowano", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else
                Toast.makeText(this, "Logowanie nie powiodło się", Toast.LENGTH_SHORT).show()
        }
    }
}