package com.log.eventscommunities.presentation.screens.home.components.event_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.log.eventscommunities.presentation.screens.home.components.add_event.TagSelector
import com.log.eventscommunities.presentation.util.common_composables.OCIconButton
import com.log.eventscommunities.ui.theme.Creme
import com.log.eventscommunities.ui.theme.Shapes
import com.log.eventscommunities.ui.theme.picList

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun FilterBox(
    setFilter: (Int) -> Unit,
) {
    var selected by remember { mutableStateOf(-1) }
    var expanded by remember { mutableStateOf(false) }
    val height = animateDpAsState(if (expanded) 200.dp else 48.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.value),
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = Creme,
            shape = Shapes.medium.copy(
                topStart = CornerSize(0.dp),
                topEnd = CornerSize(0.dp),
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(text = "Filters")
                }

                AnimatedVisibility(visible = expanded) {
                    Divider(modifier = Modifier.padding(horizontal = 12.dp))
                    TagSelector(
                        selectedIndex = selected,
                        onPicSelected = { selectedPic ->
                            selected = selectedPic
                            val selectedIndex = picList.values.indexOf(selectedPic)
                            setFilter(selectedIndex)
                        }
                    )

                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 24.dp)
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(visible = selected != -1) {
                IconButton(
                    onClick = {
                        setFilter(-1)
                        selected = -1
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "Clear filters",
                    )
                }
            }
            OCIconButton(
                state = expanded,
                onClick = {
                    expanded = !expanded
                }
            )
        }
    }
}