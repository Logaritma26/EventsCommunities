package com.log.eventscommunities.presentation.util.common_composables.text_field

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.log.eventscommunities.presentation.util.common_composables.TooltipIcon

@ExperimentalMaterialApi
@Composable
fun QTextField(
    modifier: Modifier,
    state: TextFieldState,
    onSearchCallback: (String) -> Unit = {},
) {
    var isErrorExpanded by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.then(
            Modifier
                .onFocusChanged {
                    state.onFocusChange(it.isFocused)
                }
                .padding(bottom = 8.dp)
        ),
        value = if (isErrorExpanded) "" else state.text,
        onValueChange = {
            if (!isErrorExpanded) {
                state.text = it
                onSearchCallback(it)
            } else {
                isErrorExpanded = false
            }
        },
        label = {
            AnimatedVisibility(
                visible = !isErrorExpanded
            ) {
                Text(
                    text = state.hint,
                    style = MaterialTheme.typography.body1,
                )
            }
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
            if (state.showErrors()) {
                TooltipIcon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = "Error",
                    iconTint = MaterialTheme.colors.error,
                    hint = state.getError(),
                    expandedNotifier = isErrorExpanded,
                    onDataChange = { isErrorExpanded = it }
                )
            } else if (state.text.isNotEmpty() && state.isFocused) {
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