package com.log.eventscommunities.presentation.screens.home.components.add_event

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.log.eventscommunities.presentation.util.common_composables.OCIconButton

@ExperimentalAnimationApi
@Composable
fun PeekBox(
    onClickCollapse: () -> Unit,
    isCollapsed: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = "Add New Event",
        )
        OCIconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            state = isCollapsed,
            onClick = onClickCollapse
        )
    }
}