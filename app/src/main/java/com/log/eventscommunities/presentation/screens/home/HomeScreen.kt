package com.log.eventscommunities.presentation.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.log.eventscommunities.presentation.screens.destinations.AuthScreenDestination
import com.log.eventscommunities.presentation.screens.destinations.EventDetailDestination
import com.log.eventscommunities.presentation.screens.home.components.drawer.Drawer
import com.log.eventscommunities.presentation.screens.home.components.event_list.EventList
import com.log.eventscommunities.presentation.screens.home.components.add_event.AddEvent
import com.log.eventscommunities.ui.theme.Creme
import com.log.eventscommunities.ui.theme.Shapes
import com.log.eventscommunities.ui.theme.Sharp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Destination(start = true)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state = viewModel.homeState
    val sheetEnabled = !state.isLoading() && state.user != null && state.user.isEmailVerified

    LaunchedEffect(key1 = true) {
        viewModel.reloadAuth()
        viewModel.fetchUser()
        viewModel.fetchEvents()
    }
    LaunchedEffect(key1 = state.filter.value) {
        viewModel.filterEvents()
    }

    val scaffoldState = rememberBottomSheetScaffoldState(
        drawerState = rememberDrawerState(DrawerValue.Closed),
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed),
    )
    val scope = rememberCoroutineScope()
    val goAuth = { navigator.navigate(AuthScreenDestination()) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Uni Events") }
            )
        },
        drawerContent = {
            Drawer(
                user = state.user,
                signOut = { viewModel.signOut() },
                verifyMe = { viewModel.sendVerification() },
                goAuth = goAuth
            )
        },
        drawerShape = Sharp.copy(bottomEnd = CornerSize(24.dp)),
        drawerGesturesEnabled = !state.isLoading(),
        sheetGesturesEnabled = sheetEnabled,
        sheetElevation = 16.dp,
        sheetShape = Shapes.medium,
        sheetBackgroundColor = Creme,
        sheetContent = {
            AddEvent(
                isCollapsed = scaffoldState.bottomSheetState.isCollapsed,
                onClickCollapse = {
                    onClickCollapse(
                        scaffoldState = scaffoldState,
                        sheetEnabled = sheetEnabled,
                        scope = scope,
                    )
                },
                onSendEvent = { event -> viewModel.postEvent(event) },
                user = state.user,
                notAuthenticatedRedirection = goAuth,
            )
        },
    ) {
        EventList(
            paddingValues = it,
            events = if (state.filter.value == -1) state.eventList else state.filteredList,
            isLoading = state.isLoading(),
            setFilter = { selected -> state.filter.value = selected },
            goEventDetail = { event ->
                navigator.navigate(EventDetailDestination(event = event))
            },
        )
    }
}

@ExperimentalMaterialApi
private fun onClickCollapse(
    scaffoldState: BottomSheetScaffoldState,
    sheetEnabled: Boolean,
    scope: CoroutineScope,
) {
    if (scaffoldState.bottomSheetState.isCollapsed) {
        if (sheetEnabled) {
            scope.launch { scaffoldState.bottomSheetState.expand() }
        }
    } else {
        scope.launch { scaffoldState.bottomSheetState.collapse() }
    }
}