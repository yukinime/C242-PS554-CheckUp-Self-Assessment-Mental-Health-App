package com.aruel.checkupapp.ui.result

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aruel.checkupapp.R
import com.aruel.checkupapp.data.response.CheckUpResponse
import com.aruel.checkupapp.databinding.ActivityResultBinding
import com.aruel.checkupapp.ui.main.MainActivity


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        binding.consul.setOnClickListener {
            openWhatsApp()
        }

        @Suppress("DEPRECATION")
        val result: CheckUpResponse? = intent.getParcelableExtra("RESULT_DATA")
        if (result != null) {
            displayResult(result)
        } else {
            displayError()
        }


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


    private fun displayResult(result: CheckUpResponse) {
        binding.tvPredictedClass.text = "Prediksi: ${result.predictedClass ?: "Tidak tersedia"}"
        binding.tvSuggestion.text = "Saran: ${result.suggestion ?: "Tidak tersedia"}"
        binding.tvDefinition.text = "Definisi: ${result.definition ?: "Tidak tersedia"}"
        binding.tvCategory.text = "Kategori: ${result.category ?: "Tidak tersedia"}"
        binding.tvSeverity.text = "Persentase: ${result.severity ?: "Tidak tersedia"}"
    }

    private fun displayError() {
        binding.tvPredictedClass.text = getString(R.string.error_tidak_dapat_memuat_hasil)
        binding.tvSuggestion.text = ""
        binding.tvDefinition.text = ""
        binding.tvCategory.text = ""
        binding.tvSeverity.text = ""
    }


    private fun openWhatsApp() {
        val predictedClass = binding.tvPredictedClass.text.toString()


        val message = "Halo dok!,hasil Test saya $predictedClass, Apakah ada Solusi?"

        val phoneNumber = "6285691775503"
        val url = "https://wa.me/$phoneNumber?text=${Uri.encode(message)}"

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "WhatsApp tidak ditemukan di perangkat ini", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        @Suppress("DEPRECATION")
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("navigate_to", R.id.navigation_dashboard) // Kirim data navigasi
        startActivity(intent)
        finish() // Tutup ResultActivity
    }

}

