package com.example.eventfinder

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventfinder.screens.HomeScreen
import com.example.eventfinder.screens.SearchScreen
import com.example.eventfinder.screens.EventDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(dataStore: DataStore<Preferences>) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = stringResource(R.string.top_bar_text),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        content = { innerPadding ->
            val eventsViewModel: EventsViewModel = viewModel()

            NavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                startDestination = "home"
            ) {
                composable(route = "home") {
                    HomeScreen(dataStore)
                }
                composable(route = "search") {
                    SearchScreen(navController, eventsViewModel, dataStore)
                }
                composable(route = "eventDetail/{eventId}") { backStackEntry ->
                    val eventId = backStackEntry.arguments?.getString("eventId")
                    EventDetailScreen(eventId = eventId, eventsViewModel = eventsViewModel)
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    )
}
