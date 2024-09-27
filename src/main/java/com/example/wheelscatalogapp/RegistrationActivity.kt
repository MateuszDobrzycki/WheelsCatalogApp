package com.example.wheelscatalogapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var etConfPass: EditText
    private lateinit var etPass: EditText
    private lateinit var btnSignUp: Button
    lateinit var tvRedirectRegister: TextView
    private lateinit var auth: FirebaseAuth
    private val repository = FirebaseRepository()
    private val REGISTRATION_DEBUG = "REGISTRATION_DEBUG"
    fun createNewUser(user: User){
        repository.createNewUser(user)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)

        etEmail = findViewById(R.id.registerEmailAddress)
        etConfPass = findViewById(R.id.registerConfPassword)
        etPass = findViewById(R.id.registerPassword)
        btnSignUp = findViewById(R.id.registerButton)
        tvRedirectRegister = findViewById(R.id.tvRedirectRegister)
        auth = Firebase.auth
        btnSignUp.setOnClickListener {
            signUpUser()
        }
        tvRedirectRegister.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun signUpUser() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val confirmPassword = etConfPass.text.toString()
            if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email i Hasło nie mogą być puste", Toast.LENGTH_SHORT).show()
            return
            }
            if (pass != confirmPassword) { Toast.makeText(this,
                    "Hasła muszą być takie same", Toast.LENGTH_SHORT).show()
            return
            }
            else
            auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener { authRes ->
            if (authRes.user != null){
                val user = User(
                        authRes.user!!.uid,
                        "",
                        "",
                        authRes.user!!.email,
                        listOf(), ""
                )
                createNewUser(user)
                Toast.makeText(this, "Pomyślnie zarejestrowano konto", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
                else {
                Toast.makeText(this, "Rejestracja nie powiodła się", Toast.LENGTH_SHORT).show()
            }
        }
    }
}