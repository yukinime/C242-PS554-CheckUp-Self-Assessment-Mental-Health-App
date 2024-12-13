package com.aruel.checkupapp.ui.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aruel.checkupapp.ui.main.MainActivity
import com.aruel.checkupapp.data.factory.ViewModelFactory
import com.aruel.checkupapp.data.pref.UserModel
import com.aruel.checkupapp.data.utils.NetworkUtils.isInternetAvailable
import com.aruel.checkupapp.ui.register.SignupActivity
import com.aruel.checkupapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView2.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        setupView()
        setupAction()
//        playAnimation()
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

    private fun setupAction() {
        binding.buttonlog.setOnClickListener {
            val email = binding.formEmail.text.toString().trim()
            val password = binding.formPw.text.toString().trim()

            if (email.isEmpty()) {
                binding.formEmail.error = "Email cannot be empty"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.formPw.error = "Password cannot be empty"
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

            lifecycleScope.launch {
                try {
                    val response = viewModel.login(email, password)
                    if (response.success) {
                        val loginResult = response.data
                        if (loginResult != null) {
                            viewModel.saveSession(
                                UserModel(
                                    email = email,
                                    token = loginResult.accessToken.toString(),
                                    username = loginResult.username
                                )
                            )
                        }
                        AlertDialog.Builder(this@LoginActivity).apply {
                            if (loginResult != null) {
                                setTitle("Welcome ${loginResult.username}")
                            }
                            setMessage("Login successful!")
                            setPositiveButton("Continue") { _, _ ->
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        showErrorDialog(response.message)
                    }
                } catch (e: Exception) {
                    showErrorDialog("Login failed: ${e.message}")
                } finally {
                    binding.progressBar.cancelAnimation()
                    binding.progressBar.visibility = View.GONE
                }
            }
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

//    private fun playAnimation() {
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()
//
//        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
//        val message =
//            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
//        val emailTextView =
//            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
//        val emailEditTextLayout =
//            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
//        val passwordTextView =
//            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
//        val passwordEditTextLayout =
//            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
//        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
//
//        AnimatorSet().apply {
//            playSequentially(
//                title,
//                message,
//                emailTextView,
//                emailEditTextLayout,
//                passwordTextView,
//                passwordEditTextLayout,
//                login
//            )
//            startDelay = 100
//        }.start()
//    }

}