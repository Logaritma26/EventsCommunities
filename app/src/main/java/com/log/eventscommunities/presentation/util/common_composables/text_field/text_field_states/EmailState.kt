package com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states

import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState
import java.util.regex.Pattern

class EmailState(hint: String?) : TextFieldState(
    validator = ::isEmailValid,
    errorFor = ::emailValidationError,
    pHint = hint,
)

private fun emailValidationError(email: String): String {
    if (email.isBlank()) return "Email cannot be empty"
    return "Invalid email: $email"
}

private fun isEmailValid(email: String, _empty: String?): Boolean {
    val emailValidationRegex = "^(.+)@(.+)\$"
    return Pattern.matches(emailValidationRegex, email)
}