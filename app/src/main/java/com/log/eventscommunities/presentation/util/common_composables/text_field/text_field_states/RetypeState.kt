package com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states

import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState

class RetypeState(
    hint: String?,
    retype: String,
) : TextFieldState(
    validator = ::isFieldValid,
    errorFor = ::fieldValidationError,
    pHint = hint,
    retype = retype,
)

private fun fieldValidationError(password: String): String {
    if (password.isBlank()) return "Password cannot be empty"
    return "Passwords don't match !"
}

private fun isFieldValid(password: String, retype: String?): Boolean {
    return password == retype
}
