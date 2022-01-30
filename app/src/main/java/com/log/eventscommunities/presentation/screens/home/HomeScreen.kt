package com.log.eventscommunities.presentation.screens.home

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.log.eventscommunities.presentation.screens.home.components.Drawer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    // TODO add back stack handling for not returning back to auth if authenticated

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("TopAppBar") },
                backgroundColor = Color.Magenta,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Text("X")
            }
        },
        drawerContent = { Drawer() },
        content = {
            Text("BodyContent")
        },
    )

}
