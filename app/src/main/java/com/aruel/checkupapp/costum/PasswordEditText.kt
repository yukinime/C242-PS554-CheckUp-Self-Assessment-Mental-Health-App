package com.aruel.checkupapp.costum

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.aruel.checkupapp.R

class PasswordEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        setup()
        setPadding(16, 16, 16, 16)
    }

    private fun setup() {
        // Placeholder text for password input
        hint = context.getString(R.string.password_hint)

        // Listener to monitor text changes
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun validatePassword(password: CharSequence?) {
        if (!password.isNullOrEmpty() && password.length < 8) {
            error = context.getString(R.string.password_error)
        } else {
            error = null // Clear error when valid
        }
    }
}