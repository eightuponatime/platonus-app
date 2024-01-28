package com.example.platonus_app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.platonus_app.application.StarterPage.LoginScreen
import com.example.platonus_app.ui.theme.Platonus_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Platonus_appTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {

                    //navigator to login screen
                    composable("login") {
                        LoginScreen(navController = navController)
                    }

                    //navigator to signup screen
                    composable("signup") {

                    }
                }
            }
        }
    }
}