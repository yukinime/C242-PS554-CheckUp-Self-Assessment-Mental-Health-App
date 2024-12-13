package com.aruel.checkupapp.ui.register


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aruel.checkupapp.data.factory.ViewModelFactory
import com.aruel.checkupapp.data.utils.NetworkUtils.isInternetAvailable
import com.aruel.checkupapp.databinding.ActivitySignupBinding


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private val viewModel: SignupViewModel by viewModels {
        ViewModelFactory.getInstance(this) // Memastikan menggunakan ViewModelFactory yang sudah disesuaikan
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    @Suppress("DEPRECATION")
    private fun setupAction() {
        binding.buttonsign.setOnClickListener {
            val email = binding.formEmail.text.toString()
            val name = binding.editTextText3.text.toString()
            val password = binding.formPw.text.toString()

            binding.formPw.error = null

            if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 8) {
                binding.formPw.error = "Password must be at least 8 characters long"
                return@setOnClickListener
            }
            if (!isInternetAvailable(this)) {
                showErrorDialog("No internet connection available.")
                return@setOnClickListener
            }

            binding.progressBar.visibility = View.VISIBLE
            binding.progressBar.playAnimation()

            viewModel.register(name, email, password)

            lifecycleScope.launchWhenStarted {
                viewModel.registerResult.collect { result ->
                    result?.let {
                        if (it.success) {
                            Toast.makeText(
                                this@SignupActivity,
                                "Register successful: ${it.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@SignupActivity,
                                "Register failed: ${it.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    binding.progressBar.cancelAnimation()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(500)
        val nameEditText = ObjectAnimator.ofFloat(binding.editTextText3, View.ALPHA, 1f).setDuration(500)
        val emailEditText = ObjectAnimator.ofFloat(binding.formEmail, View.ALPHA, 1f).setDuration(500)
        val passwordEditText = ObjectAnimator.ofFloat(binding.formPw, View.ALPHA, 1f).setDuration(500)
        val buttonSign = ObjectAnimator.ofFloat(binding.buttonsign, View.ALPHA, 1f).setDuration(500)
        val forgotText = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title,
                nameEditText,
                emailEditText,
                passwordEditText,
                buttonSign,
                forgotText
            )
            startDelay = 300
            start()
        }
    }
    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("OK") { _, _ -> }
            create()
            show()
        }
    }
}
