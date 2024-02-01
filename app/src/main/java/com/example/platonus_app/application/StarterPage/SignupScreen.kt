package com.example.platonus_app.application.StarterPage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.platonus_app.R
import com.example.platonus_app.data.DatabaseManager
import com.example.platonus_app.data.UserEntity
import com.example.platonus_app.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json.Default.configuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController) {
    val textColor = Color.White
    val showSuccessDialog = remember { mutableStateOf(false) }
    val showUnsuccessDialog = remember {mutableStateOf(false)}
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(0xFF282828),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = "pltns",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.dankmono))
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                val firstNameState = remember {
                    mutableStateOf("")
                }
                val lastNameState = remember {
                    mutableStateOf("")
                }
                val groupState = remember {
                    mutableStateOf("")
                }

                val usernameState = remember {
                    mutableStateOf("")
                }
                val passwordState = remember{
                    mutableStateOf("")
                }
                val scope = rememberCoroutineScope()

                val emptyFieldsList = remember {mutableStateOf(emptyList<String>())}
                val showEmptyFieldsDialog = remember {mutableStateOf(false)}

                val configuration = LocalConfiguration.current
                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(16.dp)
                ) {

                    item {
                        Card(
                            modifier = Modifier.padding(20.dp),
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(10.dp),
                            border = BorderStroke(1.dp, Color(0xff808080)),
                            colors = CardDefaults.cardColors(Color.Transparent),
                        ) {
                            Text(
                                text = "sign up",
                                color= textColor,
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.dankmono)),
                                modifier = Modifier.padding(20.dp),
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        FirstNameField(firstNameState = firstNameState, textColor = textColor)
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        LastNameField(lastNameState = lastNameState, textColor = textColor)
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        GroupField(groupState = groupState, textColor = textColor)
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .border(1.dp, Color(0xff808080), shape = RoundedCornerShape(30.dp))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "enter username and password from platonus",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.dankmono)),
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        UsernameField(usernameState, textColor)
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        PasswordField(passwordState, textColor)
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                    item {
                        Button(
                            onClick ={
                                scope.launch {
                                    val username = usernameState.value
                                    val password = passwordState.value
                                    val firstName = firstNameState.value
                                    val lastName = lastNameState.value
                                    val studyGroup = groupState.value

                                    val emptyFields = validateFields(username, password, firstName,
                                        lastName, studyGroup)

                                    if (emptyFields.isEmpty()) {
                                        val success = registerUser(username, password, firstName,
                                            lastName, studyGroup)

                                        if (success) {
                                            showSuccessDialog.value = true
                                        } else {
                                            showUnsuccessDialog.value = true
                                        }
                                    } else {
                                        showEmptyFieldsDialog.value = true
                                        emptyFieldsList.value = emptyFields
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(Color.Transparent),
                            border = BorderStroke(width = 1.dp, color = Color(0xff808080))
                        ) {
                            Text(
                                text = "sign up",
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.dankmono)),
                                color = textColor
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height((configuration.screenHeightDp * .3f).dp))

                    }

                }
                if (showEmptyFieldsDialog.value) {
                    EmptyStateAlertDialog(
                        emptyFieldsList.value,
                        onClose = {
                            showEmptyFieldsDialog.value = false
                        }
                    )
                }

                if (showSuccessDialog.value) {
                    RegistrationAlertDialog(navController) {
                        showSuccessDialog.value = false
                    }
                }
                if(showUnsuccessDialog.value) {
                    WrongRegistrationAlertDialog {
                        showUnsuccessDialog.value = false
                    }
                }
            }
        }
    }


}

suspend fun registerUser(username: String, password: String, first_name: String,
                         last_name: String, study_group: String): Boolean {
    return withContext(Dispatchers.IO) {
        val userExists = ApiClient.checkIfUserExists(username)
        if (!userExists) {
            val newUser = UserEntity(username = username, password = password)
            DatabaseManager.getDatabase().userDao().insert(newUser)
            ApiClient.sendUserDataToServer(username, password, first_name, last_name, study_group)
            true
        } else {
            false
        }
    }
}

fun validateFields(username: String, password: String, firstName: String, lastName: String, studyGroup: String): List<String> {
    val emptyFields = mutableListOf<String>()
    if (username.isEmpty()) {
        emptyFields.add("Username")
    }
    if (password.isEmpty()) {
        emptyFields.add("Password")
    }
    if (firstName.isEmpty()) {
        emptyFields.add("First Name")
    }
    if (lastName.isEmpty()) {
        emptyFields.add("Last Name")
    }
    if (studyGroup.isEmpty()) {
        emptyFields.add("Study Group")
    }
    return emptyFields
}