package com.log.eventscommunities.presentation.screens.home.components.add_event

import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState

data class AddEventState(
    val titleState: TextFieldState = TextFieldState(pHint = "Title"),
    val descriptionState: TextFieldState = TextFieldState(pHint = "Description"),
    val locationState: TextFieldState = TextFieldState(pHint = "Location"),
    val dateState: Long = System.currentTimeMillis(),
    val dateSelectionState: Boolean = false,
    val picState: Int = 1,
) {
    fun validate(): Boolean {
        return titleState.validate()
                && descriptionState.validate()
                && locationState.validate()
                && dateSelectionState
    }
}