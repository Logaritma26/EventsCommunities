package com.log.eventscommunities.presentation.util.common_composables.text_field

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

open class TextFieldState(
    private val validator: (String, String?) -> Boolean = { text, _ -> text.isNotBlank() && text.isNotEmpty() },
    private val errorFor: (String) -> String = { "" },
    private var pHint: String? = null,
    var retype: String? = null,
) {
    private var isFocusedDirty: Boolean by mutableStateOf(false)
    var isFocused: Boolean by mutableStateOf(false)
    private var displayErrors: Boolean by mutableStateOf(false)

    var text: String by mutableStateOf("")
    var hint: String by mutableStateOf("").apply {
        pHint?.let {
            this.value = it
        }
    }

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    private fun enableShowErrors() {
        if (isFocusedDirty) {
            displayErrors = true
        }
    }

    fun showErrors() = !isValid && displayErrors

    fun validate(): Boolean {
        val res = validator(text, retype)
        if (res) {
            displayErrors = false
        } else {
            enableShowErrors()
        }
        return res
    }

    open val isValid: Boolean
        get() = validator(text, retype)

    open fun getError(): String {
        return if (showErrors()) {
            errorFor(text)
        } else {
            "Invalid input!"
        }
    }
}