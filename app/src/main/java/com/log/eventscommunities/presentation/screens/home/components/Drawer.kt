package com.log.eventscommunities.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Drawer() {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(4) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {

                        },
                    text = "Title 1"
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {

                        },
                    text = "Title 2"
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {

                        },
                    text = "Title 3"
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {

                        },
                    text = "Title 4"
                )
            }
        }
    }
}