package com.example.eventfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eventfinder.ui.theme.EventFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventFinderTheme {
                MainScreen(dataStore = applicationContext.dataStore)
            }
        }
    }
}
