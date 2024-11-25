package com.aruel.checkupapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.aruel.checkupapp.databinding.ActivityLoginBinding
import com.aruel.checkupapp.databinding.ActivityMainBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.textView2.setOnClickListener() {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

    }
}