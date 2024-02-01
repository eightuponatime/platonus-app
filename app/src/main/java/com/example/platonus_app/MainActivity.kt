package com.example.platonus_app

import android.os.Bundle
import android.view.View
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
import androidx.navigation.navArgument
import com.example.platonus_app.application.Arguments.MainPageArguments
import com.example.platonus_app.application.Arguments.OfflineScheduleArguments
import com.example.platonus_app.application.Arguments.SchedulePageArguments
import com.example.platonus_app.application.Arguments.UserListPageArguments
import com.example.platonus_app.application.Arguments.UserPageArguments
import com.example.platonus_app.application.LoggedPage.MainPage
import com.example.platonus_app.application.LoggedPage.SchedulePage
import com.example.platonus_app.application.LoggedPage.UserListPage
import com.example.platonus_app.application.LoggedPage.UserPage
import com.example.platonus_app.application.StarterPage.LoginScreen
import com.example.platonus_app.application.StarterPage.OfflineSchedule
import com.example.platonus_app.application.StarterPage.SignupScreen
import com.example.platonus_app.data.DatabaseManager
import com.example.platonus_app.ui.theme.Platonus_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseManager.initialize(applicationContext)
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
                        SignupScreen(navController = navController)
                    }

                    //navigator to user list page
                    composable(
                        route = "user-list/{username}/{password}/{name}/{group}/{password}",
                        arguments = listOf(
                            navArgument("username") {},
                            navArgument("password") {},
                            navArgument("name") {},
                            navArgument("group") {}
                        )
                    ) {backStackEntry ->
                        val arguments = UserListPageArguments(
                            username = backStackEntry.arguments?.getString("username") ?: "",
                            password = backStackEntry.arguments?.getString("password") ?: "",
                            name =backStackEntry.arguments?.getString("name") ?: "",
                            group = backStackEntry.arguments?.getString("group") ?: ""
                        )
                        UserListPage(
                            navController = navController,
                            username = arguments.username,
                            password = arguments.password,
                            name = arguments.name,
                            group = arguments.group
                        )
                    }

                    //navigator to main page
                    composable(
                        route = "main/{username}/{password}/{name}/{group}",
                        arguments = listOf(
                            navArgument("username") {},
                            navArgument("password") {},
                            navArgument("name") {},
                            navArgument("group") {}
                        )
                    ) { backStackEntry ->
                        val arguments = MainPageArguments(
                            username = backStackEntry.arguments?.getString("username") ?: "",
                            password = backStackEntry.arguments?.getString("password") ?: "",
                            name = backStackEntry.arguments?.getString("name") ?: "",
                            group = backStackEntry.arguments?.getString("group") ?: ""
                        )
                        MainPage(
                            navController = navController,
                            username = arguments.username,
                            password = arguments.password,
                            name = arguments.name,
                            group = arguments.group
                        )
                    }

                    //navigator to user page
                    composable(
                        route = "user-page/{username}/{password}/{name}/{group}/{user_username}/{user_name}/{user_group}",
                        arguments = listOf(
                            navArgument("username") {},
                            navArgument("password") {},
                            navArgument("name") {},
                            navArgument("group") {},
                            navArgument("user_username") {},
                            navArgument("user_name") {},
                            navArgument("user_group") {}
                        )
                    ) {backStackEntry ->
                        val arguments = UserPageArguments(
                            username = backStackEntry.arguments?.getString("username") ?: "",
                            password = backStackEntry.arguments?.getString("password") ?: "",
                            name = backStackEntry.arguments?.getString("name") ?: "",
                            group = backStackEntry.arguments?.getString("group") ?: "",
                            user_username = backStackEntry.arguments?.getString("user_username") ?: "",
                            user_name = backStackEntry.arguments?.getString("user_name") ?: "",
                            user_group = backStackEntry.arguments?.getString("user_group") ?: ""
                        )
                        UserPage(
                            navController = navController,
                            username = arguments.username,
                            password = arguments.password,
                            name = arguments.name,
                            group = arguments.group,
                            user_username = arguments.user_username,
                            user_name = arguments.user_name,
                            user_group = arguments.user_group
                        )

                    }

                    //navigator to user page
                    composable(
                        route = "schedule/{username}/{password}/{name}/{group}",
                        arguments = listOf(
                            navArgument("username") {},
                            navArgument("password") {},
                            navArgument("name") {},
                            navArgument("group") {},
                        )
                    ) { backStackEntry ->
                        val arguments = SchedulePageArguments(
                            username = backStackEntry.arguments?.getString("username") ?: "",
                            password = backStackEntry.arguments?.getString("password") ?: "",
                            name = backStackEntry.arguments?.getString("name") ?: "",
                            group = backStackEntry.arguments?.getString("group") ?: "",
                        )
                        SchedulePage(
                            navController = navController,
                            username = arguments.username,
                            password = arguments.password,
                            name = arguments.name,
                            group = arguments.group,
                        )
                    }

                    //navigator to offline schedule page
                    composable(
                        route = "offline-schedule/{username}",
                        arguments = listOf(
                            navArgument("username") {}
                        )
                    ) { backStackEntry ->
                        val arguments = OfflineScheduleArguments(
                            username = backStackEntry.arguments?.getString("username") ?: ""
                        )
                        OfflineSchedule(navController = navController, username = arguments.username)
                    }
                }
            }
        }
    }
}