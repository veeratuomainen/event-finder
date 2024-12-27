package com.example.eventfinder.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.eventfinder.EventResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.eventfinder.R
import com.example.eventfinder.EventsViewModel
import com.example.eventfinder.RetrofitInstance

@Composable
fun SearchScreen(navController: NavHostController, eventsViewModel: EventsViewModel = viewModel(), dataStore: DataStore<Preferences>) {
    var city by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.search_title, city),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        var eventResponse by remember { mutableStateOf<EventResponse?>(null) }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            city = dataStore.data.first()[stringPreferencesKey("city_key")] ?: "Tampere"

            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val response = RetrofitInstance.apiService.getEventResponse(city)
                    withContext(Dispatchers.Main) {
                        eventResponse = response
                        val events = response._embedded.events
                        eventsViewModel.updateEvents(events.orEmpty())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        eventResponse = null
                    }
                }
            }
        }

        val events = eventResponse?._embedded?.events.orEmpty()

        if (events.isNotEmpty()) {
            LazyColumn {
                items(events) { event ->
                    Text(
                        text = event.name ?: stringResource(R.string.unknown),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("eventDetail/${event.id}")
                            }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
        else {
            Text(text = stringResource(R.string.loading))
        }
    }
}
