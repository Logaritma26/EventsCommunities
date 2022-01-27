package com.log.eventscommunities.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.log.eventscommunities.presentation.screens.home.NavGraphs
import com.log.eventscommunities.ui.theme.EventsCommunitiesTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventsCommunitiesTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}