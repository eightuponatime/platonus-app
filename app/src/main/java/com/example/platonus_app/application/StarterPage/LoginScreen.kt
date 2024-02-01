package com.example.platonus_app.application.StarterPage

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.platonus_app.R
import com.example.platonus_app.data.DatabaseManager
import com.example.platonus_app.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavController) {
    val textColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(20.dp)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier.padding(20.dp),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(10.dp),
                        border = BorderStroke(1.dp, Color(0xff808080)),
                        colors = CardDefaults.cardColors(Color.Transparent),
                    ) {
                        Text(
                            text = "platonus",
                            color= textColor,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.dankmono)),
                            modifier = Modifier.padding(20.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))


                    val context = LocalContext.current
                    val userManager = remember { UserManager(context) }

                    val usernameState = remember {
                        mutableStateOf(userManager.getUsername() ?: "")
                    }
                    UsernameTextField(textColor, usernameState)
                    Spacer(modifier = Modifier.height(8.dp))

                    val passwordState = remember {
                        mutableStateOf(userManager.getPassword() ?: "")
                    }
                    PasswordTextField(textColor, passwordState)

                    var isChecked by remember {
                        mutableStateOf(userManager.isRememberMeEnabled())
                    }

                    Spacer(Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "remember me",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.dankmono)),
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = 14.sp
                        )

                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = {
                                isChecked = it
                                if (it) {
                                    userManager.saveUserData(usernameState.value, passwordState.value)
                                } else {
                                    userManager.clearUserData()
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xffffffff),
                                uncheckedColor = Color.LightGray
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    var authenticationState by remember { mutableStateOf(AuthenticationState.INITIAL) }
                    val scope = rememberCoroutineScope()
                    val showUnsuccessfulDialog = remember {mutableStateOf(false)}

                    val emptyFieldsList = remember { mutableStateOf(emptyList<String>()) }
                    val showEmptyFieldsDialog = remember { mutableStateOf(false) }

                    Button(
                        onClick = {
                            scope.launch {
                                val username = usernameState.value
                                val password = passwordState.value

                                val emptyFields = validateFields(username, password)

                                if(emptyFields.isEmpty()) {
                                    authenticationState =
                                        if (ApiClient.logInDatabase(username, password)) {
                                            AuthenticationState.AUTHENTICATED
                                        } else {
                                            AuthenticationState.UNAUTHENTICATED
                                        }

                                    when (authenticationState) {
                                        AuthenticationState.AUTHENTICATED -> {

                                            val name = ApiClient.getFirstName(username)
                                            val group = ApiClient.getGroup(username)
                                            navController.navigate("main/$username/$password/$name/$group")
                                        }

                                        AuthenticationState.UNAUTHENTICATED -> {
                                            showUnsuccessfulDialog.value = true
                                        }

                                        else -> {
                                            showUnsuccessfulDialog.value = true
                                        }
                                    }
                                } else {
                                    showEmptyFieldsDialog.value = true
                                    emptyFieldsList.value = emptyFields
                                }
                            }

                        },
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .height(50.dp),
                        border = BorderStroke(width = 1.dp, color = Color(0xff808080)),
                    ) {
                        Text(
                            text = "log in",
                            color = textColor,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.dankmono))
                        )
                    }

                    if (showEmptyFieldsDialog.value) {
                        EmptyStateAlertDialog(
                            emptyFieldsList.value,
                            onClose = {
                                showEmptyFieldsDialog.value = false
                            }
                        )
                    }

                    if(showUnsuccessfulDialog.value) {
                        WrongLoginAlertDialog {
                            showUnsuccessfulDialog.value = false
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val showScheduleButton = remember { mutableStateOf(false) }

                    if (isChecked) {
                        val username = usernameState.value
                        val scheduleDao = DatabaseManager.getDatabase().scheduleDao()

                        LaunchedEffect(username) {
                            val scheduleEntity = withContext(Dispatchers.IO) {
                                scheduleDao.getScheduleByUsername(username)
                            }

                            if (scheduleEntity != null) {
                                showScheduleButton.value = true
                            }
                        }
                    }

                    if (showScheduleButton.value) {
                        Button(
                            onClick = {
                                navController.navigate("offline-schedule/${usernameState.value}")
                            },
                            colors = ButtonDefaults.buttonColors(Color.Transparent),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth()
                                .height(50.dp),
                            border = BorderStroke(1.dp, Color(0xff808080))
                        ) {
                            Text(
                                text = "schedule",
                                color = textColor,
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.dankmono)),
                                modifier = Modifier.align(Alignment.CenterVertically))
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(onClick = {navController.navigate("signup")},
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        border = BorderStroke(1.dp, Color(0xff808080))
                    ) {
                        Text(
                            text = "don't have an account?",
                            color = textColor,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.dankmono)),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }


                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameTextField(textColor: Color, usernameState: MutableState<String>) {
    val buttonColor = Color(0xff282828)
    OutlinedTextField(
        value = usernameState.value,
        onValueChange = { usernameState.value = it },
        label = { Text(
            text = "username",
            fontFamily = FontFamily(Font(R.font.dankmono))
        )},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            focusedBorderColor = Color.LightGray,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color(0xff808080),
            disabledBorderColor = Color(0xff808080),
            disabledTextColor = Color(0xff808080),
        )
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(textColor: Color, passwordState: MutableState<String>) {
    val buttonColor = Color(0xFF7289DA)
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(
            text = "password",
            fontFamily = FontFamily(Font(R.font.dankmono))
        )},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            focusedBorderColor = Color.LightGray,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color(0xff808080),
            disabledBorderColor = Color(0xff808080),
            disabledTextColor = Color(0xff808080),
            disabledSupportingTextColor = Color(0xff808080),
            disabledLeadingIconColor = Color(0xff808080),
            disabledTrailingIconColor = Color(0xff808080),
            focusedLeadingIconColor = Color(0xff808080),
            unfocusedLeadingIconColor = Color(0xff808080)
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(imageVector  = image, description)
            }
        }
    )
}
/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(textColor: Color, passwordState: MutableState<String>) {
    val buttonColor = Color(0xFF7289DA)
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text("Password") },
        singleLine = true,
        placeholder = { Text("Password") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(imageVector  = image, description)
            }
        }
    )
}*/

@Composable
fun GradientBackgroundBrush(isVerticalGradient: Boolean, colors: List<Color>): Brush {
    val endOffSet = if (isVerticalGradient) {
        Offset(0f, Float.POSITIVE_INFINITY)
    } else {
        Offset(Float.POSITIVE_INFINITY, 0f)
    }
    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = endOffSet
    )
}

@Composable
fun WrongLoginAlertDialog(onClose: () -> Unit) {
    AlertDialog(
        containerColor = Color(0xff282828),
        onDismissRequest = {
            onClose()
        },
        title = {
            Text(
                text = "Warning",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.dankmono))
            )
        },
        text = {
            Text(
                text = "wrong login or password",
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.dankmono))
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        onClose()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    border = BorderStroke(width = 1.dp, color = Color(0xff808080))
                ) {
                    Text(
                        text = "ok",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.dankmono))
                    )
                }
            }
        }
    )
}


fun validateFields(username: String, password: String): List<String> {
    val emptyFields = mutableListOf<String>()
    if (username.isEmpty()) {
        emptyFields.add("Username")
    }
    if (password.isEmpty()) {
        emptyFields.add("Password")
    }
    return emptyFields
}