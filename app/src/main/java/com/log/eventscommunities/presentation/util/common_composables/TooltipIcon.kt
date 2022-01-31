package com.log.eventscommunities.presentation.util.common_composables

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun TooltipIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    iconTint: Color,
    hint: String,
    expandedNotifier: Boolean? = null,
    onDataChange: (Boolean) -> Unit = {},
) {
    var time by remember { mutableStateOf(System.currentTimeMillis()) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        val context = LocalContext.current
        AnimatedVisibility(
            visible = expandedNotifier ?: true,
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(0.75f),
                elevation = 4.dp,
                onClick = {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - time > 2000) {
                        time = currentTime
                        Toast
                            .makeText(context, hint, Toast.LENGTH_SHORT)
                            .show()
                    }
                },
            ) {
                Text(
                    text = hint,
                    modifier = Modifier
                        .weight(weight = 1f)
                        .padding(6.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = { if (expandedNotifier != null) onDataChange(!expandedNotifier) },
            enabled = expandedNotifier != null,
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription ?: "Tooltip Icon",
                tint = iconTint,
            )
        }
    }
}