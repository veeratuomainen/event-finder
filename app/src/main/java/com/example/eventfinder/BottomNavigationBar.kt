package com.example.eventfinder

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.nav_label_home)) },
            label = { Text(text = stringResource(R.string.nav_label_home)) },
            selected = navController.currentDestination?.route == "home",
            onClick = { navController.navigate(route = "home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = stringResource(R.string.nav_label_search)) },
            label = { Text(text = stringResource(R.string.nav_label_search)) },
            selected = navController.currentDestination?.route == "search",
            onClick = { navController.navigate(route = "search") }
        )
    }
}
