package com.aruel.checkupapp.costum

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText


class EmailEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(charSequence)
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })
    }

    private fun validateEmail(charSequence: CharSequence?) {
        val email = charSequence?.toString()?.trim()
        when {
            email.isNullOrEmpty() -> {
                error = "Email tidak boleh kosong"
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                error = "Invalid email format"
            }
            else -> {
                error = null
            }
        }
    }
}