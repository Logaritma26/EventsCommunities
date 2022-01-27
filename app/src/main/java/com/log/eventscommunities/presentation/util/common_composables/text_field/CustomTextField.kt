package com.log.yugiohcards.lib.presentation.util.common_composables.text_field

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState

@Composable
fun CustomTextField(
    modifier: Modifier,
    state: TextFieldState,
    onSearchCallback: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.then(
            Modifier
                .onFocusChanged {
                    state.onFocusChange(it.isFocused)
                }
                .padding(bottom = 8.dp)
        ),
        value = state.text,
        onValueChange = {
            state.text = it
            onSearchCallback(it)
        },
        label = {
            Text(
                text = state.hint,
                style = MaterialTheme.typography.body1,
            )
        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        isError = state.showErrors(),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            cursorColor = Color.Black,
            disabledLabelColor = Color.White,
        ),
        trailingIcon = {
            if (state.text.isNotEmpty() && state.isFocused) {
                IconButton(
                    onClick = { state.text = "" }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Clear Field",
                    )
                }
            }
        }
    )
}