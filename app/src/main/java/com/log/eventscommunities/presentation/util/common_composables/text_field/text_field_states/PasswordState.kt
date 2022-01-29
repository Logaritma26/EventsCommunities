package com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states

import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState

class PasswordState(hint: String?) : TextFieldState(
    validator = ::isPasswordValid,
    errorFor = ::passwordValidationError,
    pHint = hint,
)

private fun passwordValidationError(password: String): String {
    if (password.isBlank()) return "Password cannot be empty"
    if (password.length < 7) return "Password should be at least 8 digit!"
    return "Invalid password: $password"
}

private fun isPasswordValid(password: String, _empty: String?): Boolean {
    return password.length >= 8
}