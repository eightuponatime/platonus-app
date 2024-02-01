package com.example.platonus_app.application.StarterPage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.platonus_app.R
import org.jetbrains.exposed.sql.transactions.transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstNameField(firstNameState: MutableState<String>, textColor: Color) {
    val firstNameValue = firstNameState.value
    val buttonColor = Color(0xFF7289DA)
    OutlinedTextField(
        value = firstNameValue,
        onValueChange = { firstNameState.value = it },
        label = { Text(
            text = "first name",
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
fun LastNameField(lastNameState: MutableState<String>, textColor: Color) {
    val lastNameValue = lastNameState.value
    val buttonColor = Color(0xFF7289DA)
    OutlinedTextField(
        value = lastNameValue,
        onValueChange = { lastNameState.value = it },
        label = { Text(
            text = "last name",
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
fun GroupField(groupState: MutableState<String>, textColor: Color) {
    val groupValue = groupState.value
    val buttonColor = Color(0xFF7289DA)
    OutlinedTextField(
        value = groupValue,
        onValueChange = { groupState.value = it },
        label = { Text(
            text = "study group",
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
fun UsernameField(usernameState: MutableState<String>, textColor: Color) {
    val usernameValue = usernameState.value
    val buttonColor = Color(0xFF7289DA)
    OutlinedTextField(
        value = usernameValue,
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
fun PasswordField(passwordState: MutableState<String>, textColor: Color) {
    val passwordValue = passwordState.value
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

@Composable
fun RegistrationAlertDialog(navController: NavController, onClose: () -> Unit) {
    AlertDialog(
        containerColor = Color(0xff282828),
        onDismissRequest = {
            onClose()
        },
        title = {
            Text(
                text = "registered successfully.",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.dankmono))
            )
        },
        text = {
            Text(
                text = "you have successfully signed up!",
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
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    border = BorderStroke(width = 1.dp, color = Color(0xff808080))
                ) {
                    Text(
                        text = "move to log in screen",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.dankmono))
                    )
                }
            }
        }
    )
}

@Composable
fun WrongRegistrationAlertDialog(onClose: () -> Unit) {
    AlertDialog(
        containerColor = Color(0xff282828),
        onDismissRequest = {
            onClose()
        },
        title = {
            Text(
                text = "warning",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.dankmono))
            )
        },
        text = {
            Text(
                text = "this user is already in database",
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.dankmono))
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
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

@Composable
fun EmptyStateAlertDialog(stateName: List<String>, onClose: () -> Unit) {
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
                text = "$stateName can't be empty",
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.dankmono))
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
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