package com.example.eventfinder.screens

import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import java.time.LocalTime
import com.example.eventfinder.R
import com.example.eventfinder.EventsViewModel
import java.time.ZoneId

@Composable
fun EventDetailScreen(eventId: String?, eventsViewModel: EventsViewModel = viewModel()) {
    val events by remember { mutableStateOf(eventsViewModel.events) }
    val event = events.find { it.id == eventId }

    val context = LocalContext.current

    if (events.isEmpty()) {
        Text(text = stringResource(R.string.loading))
    }
    else if (event != null) {
        Column (
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = event.name ?: stringResource(R.string.unknown),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.detail_promoter, event.promoter?.name ?: stringResource(R.string.unknown))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.detail_category, event.classifications?.firstOrNull()?.segment?.name ?: stringResource(R.string.unknown))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.detail_venue, event._embedded?.venues?.firstOrNull()?.name ?: stringResource(R.string.unknown))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.detail_date, event.dates?.start?.localDate?: stringResource(R.string.unknown))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.detail_start_time, event.dates?.start?.localTime?: stringResource(R.string.unknown))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.add_to_calendar)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val intent = Intent(Intent.ACTION_INSERT).apply {
                    data = CalendarContract.Events.CONTENT_URI
                    putExtra(CalendarContract.Events.TITLE, event.name)

                    val date = LocalDate.parse(event.dates?.start?.localDate)
                    val time = LocalTime.parse(event.dates?.start?.localTime)
                    val datetime = date.atTime(time.hour, time.minute)
                    val zoneId = ZoneId.systemDefault()
                    val startMillis = datetime.atZone(zoneId).toInstant().toEpochMilli()
                    putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                }
                context.startActivity(intent)
            }) {
                Text(text = stringResource(R.string.add_button_text))
            }
        }
    }
    else {
        Text(text = stringResource(R.string.not_found))
    }
}
