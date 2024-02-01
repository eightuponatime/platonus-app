package com.example.platonus_app.application.LoggedPage

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserPage(
    navController: NavController,
    username: String,
    password: String,
    name: String,
    group: String,
    user_username: String,
    user_name: String,
    user_group: String
) {
    val textColor = Color.White
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
}