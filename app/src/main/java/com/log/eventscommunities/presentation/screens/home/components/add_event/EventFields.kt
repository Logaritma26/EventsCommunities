package com.log.eventscommunities.presentation.screens.home.components.add_event

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState
import com.log.eventscommunities.presentation.util.common_composables.text_field.WTextField

@ExperimentalMaterialApi
@Composable
fun EventFields(
    titleState: TextFieldState,
    descState: TextFieldState,
    locationState: TextFieldState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WTextField(
            modifier = Modifier.fillMaxWidth(),
            state = titleState,
        )
        Spacer(modifier = Modifier.height(24.dp))
        WTextField(
            modifier = Modifier.fillMaxWidth(),
            state = descState,
            maxLines = 4,
        )
        Spacer(modifier = Modifier.height(24.dp))
        WTextField(
            modifier = Modifier.fillMaxWidth(),
            state = locationState,
        )
    }
}