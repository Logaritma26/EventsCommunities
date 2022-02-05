package com.log.eventscommunities.presentation.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.wrappers.Resource
import com.log.eventscommunities.presentation.screens.destinations.AuthScreenDestination
import com.log.eventscommunities.presentation.screens.home.components.AddEvent
import com.log.eventscommunities.presentation.screens.home.components.Drawer
import com.log.eventscommunities.presentation.util.common_composables.LoadingWidget
import com.log.eventscommunities.ui.theme.Creme
import com.log.eventscommunities.ui.theme.Shapes
import com.log.eventscommunities.ui.theme.Sharp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Destination(start = true)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    LaunchedEffect(key1 = true) {
        viewModel.fetchUser()
    }
    val state = viewModel.homeState

    val navigateToAuth = {
        navigator.navigate(AuthScreenDestination())
    }
    val scaffoldState = rememberBottomSheetScaffoldState(
        drawerState = rememberDrawerState(DrawerValue.Closed),
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed),
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("TopAppBar") }
            )
        },
        drawerContent = { Drawer() },
        drawerShape = Sharp.copy(bottomEnd = CornerSize(24.dp)),
        drawerGesturesEnabled = !state.isLoading(),
        sheetGesturesEnabled = !state.isLoading(),
        sheetShape = Shapes.medium,
        sheetBackgroundColor = Creme,
        sheetContent = {
            AddEvent(
                isCollapsed = scaffoldState.bottomSheetState.isCollapsed,
                onClickCollapse = {
                    if (scaffoldState.bottomSheetState.isCollapsed) {
                        if (!state.isLoading()) {
                            scope.launch { scaffoldState.bottomSheetState.expand() }
                        }
                    } else {
                        scope.launch { scaffoldState.bottomSheetState.collapse() }
                    }
                },
                onSendEvent = { event -> viewModel.postEvent(event) },
                user = state.user
            ) { navigateToAuth() }
        },
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text("BodyContent")

            LoadingWidget(state = state.isLoading())
        }

    }

}

data class HomeState(
    val addEventState: Resource<Any> = Resource.Initial(),
    val user: FirebaseUser? = null,
) {
    fun isLoading(): Boolean = addEventState::class == Resource.Loading<Any>()::class
}
