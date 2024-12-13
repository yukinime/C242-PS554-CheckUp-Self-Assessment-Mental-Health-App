package com.aruel.checkupapp.ui.checkup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.aruel.checkupapp.R
import com.aruel.checkupapp.data.factory.ViewModelFactory
import com.aruel.checkupapp.data.response.CheckUpResponse
import com.aruel.checkupapp.data.utils.NetworkUtils
import com.aruel.checkupapp.ui.result.ResultActivity


class CheckupFragment : Fragment(R.layout.fragment_checkup) {

    private val checkupViewModel: CheckupViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private val questions = listOf(
        "Apakah Anda merasa sedih atau cemas dalam kehidupan sehari-hari?",
        "Apakah Anda kesulitan tidur atau merasa terlalu banyak tidur?",
        "Apakah Anda merasa tidak ada harapan atau tujuan dalam hidup?",
        "Apakah Anda merasa lelah sepanjang waktu meskipun sudah tidur cukup?",
        "Apakah Anda sering merasa terisolasi atau sendirian?",
        "Apakah Anda merasa kesulitan untuk melakukan aktivitas yang biasanya Anda nikmati?",
        "Apakah Anda merasa putus asa atau tidak berdaya?",
        "Apakah Anda sering merasa gelisah atau cemas?",
        "Apakah Anda merasa sulit untuk berkonsentrasi?",
        "Apakah Anda merasa seperti dunia ini tidak adil terhadap Anda?",
        "Apakah Anda merasa ada yang salah dengan diri Anda atau kehidupan Anda?",
        "Apakah Anda merasa sangat mudah tersinggung atau marah?",
        "Apakah Anda merasa cemas tentang masa depan?",
        "Apakah Anda merasa ingin menarik diri dari orang lain?",
        "Apakah Anda merasa tidak berharga atau tidak berguna?",
        "Apakah Anda sering merasa tidak bersemangat untuk melakukan aktivitas yang biasa Anda nikmati?"
    )
    private var currentQuestionIndex = 0
    private val answers = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvQuestion = view.findViewById<TextView>(R.id.tvQuestion)
        val rgAnswers = view.findViewById<RadioGroup>(R.id.rgAnswers)
        val btnNext = view.findViewById<Button>(R.id.btnNext)
        val btnBack = view.findViewById<Button>(R.id.btnBack)

        displayQuestion(tvQuestion, rgAnswers)

        btnNext.setOnClickListener {
            val selectedOption = rgAnswers.checkedRadioButtonId
            if (selectedOption != -1) {
                val answer = view.findViewById<RadioButton>(selectedOption).text.toString()
                saveAnswer(answer)
                navigateToNextQuestion(tvQuestion, rgAnswers, btnNext)
            } else {
                Toast.makeText(context, "Pilih jawaban dulu!", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                displayQuestion(tvQuestion, rgAnswers)
            }
        }
    }

    private fun displayQuestion(tvQuestion: TextView, rgAnswers: RadioGroup) {
        tvQuestion.text = questions[currentQuestionIndex]
        rgAnswers.clearCheck()
    }

    private fun saveAnswer(answer: String) {
        if (currentQuestionIndex < answers.size) {
            answers[currentQuestionIndex] = answer
        } else {
            answers.add(answer)
        }
    }



    private fun submitAnswersToServer() {
        if (!NetworkUtils.isInternetAvailable(requireContext())) {
            Toast.makeText(
                context,
                "Tidak ada koneksi internet. Jawaban tidak dapat dikirim.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        showLoading()
        checkupViewModel.submitCheckup(
            answers,
            onSuccess = { result->
                hideLoading()
                val history = checkupViewModel.mapResponseToHistory(result)
                checkupViewModel.saveHistory(history)
                navigateToResultActivity(result)
            },
            onError = { error ->
                hideLoading()
                Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        )
    }



    private fun navigateToResultActivity(result: CheckUpResponse) {
        val intent = Intent(requireContext(), ResultActivity::class.java).apply {
            putExtra("RESULT_DATA", result)
        }
        startActivity(intent)
    }

    private fun navigateToNextQuestion(tvQuestion: TextView, rgAnswers: RadioGroup, btnNext: Button) {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            displayQuestion(tvQuestion, rgAnswers)
        } else {
            showResult()
        }
    }

    private fun showLoading() {
        val lottieLoading = view?.findViewById<LottieAnimationView>(R.id.lottieLoading)
        lottieLoading?.visibility = View.VISIBLE
        lottieLoading?.playAnimation()
    }

    private fun hideLoading() {
        val lottieLoading = view?.findViewById<LottieAnimationView>(R.id.lottieLoading)
        lottieLoading?.visibility = View.GONE
        lottieLoading?.cancelAnimation()
    }

    private fun showResult() {
        submitAnswersToServer()
    }

}
