@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.platonus_app.application.LoggedPage

import android.content.Context
import android.net.Uri
import android.view.MenuItem
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.platonus_app.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.nio.file.Files

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    navController: NavController,
    username: String,
    password: String,
    name: String,
    group: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val menuItems = listOf(
            MenuItem(
                Icons.Default.Home, "main page", onClick =
            {navController.navigate("main/$username/$password/$name/$group")}),
            MenuItem(
                Icons.Default.Face, "user List", onClick =
            {navController.navigate("user-list/$username/$password/$name/$group")})
        )
        val selectedItem = remember { mutableStateOf(menuItems[0]) }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = Color.Black
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black)
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(Color.Transparent)
                        ) {
                            CreateImageDrawer(username)
                            if(drawerState.isOpen) {
                                Row() {
                                    Text(
                                        color = Color(0xFFf8961e),
                                        fontSize = 15.sp,
                                        fontFamily = FontFamily(Font(R.font.dankmono)),
                                        text = "user@${username}:~$ "
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    TypingText(text = "show menu")
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    menuItems.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.label) },
                            selected = item == selectedItem.value,
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color.Transparent,
                                unselectedContainerColor = Color.Transparent,
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color.White,
                                selectedTextColor = Color.White,
                                unselectedTextColor = Color.White,
                            ),
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedItem.value = item
                                item.onClick()
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            },
            content = {
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
                                IconButton(onClick = {scope.launch { drawerState.open() } }) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "Localized description",
                                        tint = Color.White
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { navController.navigate("login") }) {
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
                            .padding(innerPadding)
                            .background(Color.Black),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CreateImageProfile(username)
                            Spacer(Modifier.height(16.dp))
                            CreateInfo(username, name, group)
                            Spacer(Modifier.height(16.dp))


                            Button(
                                onClick = {
                                    navController.navigate("schedule/$username/$password/$name/$group")
                                },
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .height(50.dp)
                                    .width(300.dp),
                                border = BorderStroke(1.dp, Color(0xff808080))
                            ) {
                                Text(
                                    text = "schedule",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.dankmono))
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    navController.navigate("plan/$username/$password/$name/$group")
                                },
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .height(50.dp)
                                    .width(300.dp),
                                border = BorderStroke(1.dp, Color(0xff808080))
                            ) {
                                Text(
                                    text = "individual plan",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.dankmono))
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

data class MenuItem(
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit,
)

@Composable
fun CreateImageProfile(username: String) {

    val context = LocalContext.current

    val imageUri = rememberSaveable() {
        mutableStateOf("")
    }
    val painter = rememberAsyncImagePainter(
        if (imageUri.value.isEmpty()) {
            val fileName = "image_${username}.jpg"
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                file.toUri()
            } else {
                R.drawable.ic_user
            }
        } else {
            imageUri.value
        }
    )

    val isImageSelected = imageUri.value.isNotEmpty()

    var isSaveButtonClicked by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
            uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
        isSaveButtonClicked = false
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .size(200.dp)
                .padding(5.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            shadowElevation = 4.dp
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val scope = rememberCoroutineScope()

        if (isImageSelected && !isSaveButtonClicked) {
            Button(
                onClick = {
                    scope.launch {
                        isSaveButtonClicked = true
                        saveImageToInternalStorage(context, Uri.parse(imageUri.value), username)
                        val fileName = "image_${username}.jpg"
                        val file = File(context.filesDir, fileName)
                        val imageFilePath = file.absolutePath
                        val imageByteArray = imageToByteArray(imageFilePath)
                        /*try {
                            setImage(username, imageByteArray)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }*/
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF7289DA))
            ) {
                Text(
                    text = "Save image",
                    fontFamily = FontFamily(Font(R.font.dankmono))
                )
            }
        }
    }
}

fun imageToByteArray(imageFilePath: String): ByteArray {
    val imageFile = File(imageFilePath)
    return Files.readAllBytes(imageFile.toPath())
}

fun saveImageToInternalStorage(context: Context, uri: Uri, username: String) {
    val fileName = "image_${username}.jpg"
    val inputStream = context.contentResolver.openInputStream(uri)
    val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
}

@Composable
fun CreateImageDrawer(username: String) {
    val context = LocalContext.current
    //val fileName = "image_${username}.jpg"
    //val file = File(context.filesDir, fileName)
    val painter = rememberAsyncImagePainter(
        //if (file.exists()) {
          //  file.toUri()
        //} else {
            R.drawable.heart
        //}
    )
    Surface(
        modifier = Modifier
            .size(200.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        shadowElevation = 4.dp
    ) {
        Image(
            painter = painter,
            contentDescription = "profile image",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Crop
        )

    }
}

@Composable
fun CreateInfo(
    username: String,
    name: String,
    group: String
) {
    Card(
        modifier = Modifier.padding(20.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        border = BorderStroke(1.dp, Color(0xff808080)),
        colors = CardDefaults.cardColors(Color.Transparent),
    ) {
        Text(
            text = "username: $username\nname: $name\ngroup: $group",
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.dankmono)),
            modifier = Modifier.padding(20.dp),
        )
    }
}

@Composable
fun TypingText(text: String) {
    var visibleText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        for (i in text.indices) {
            delay(50)
            visibleText = text.substring(0, i + 1)
        }
    }

    Text(
        text = visibleText,
        fontSize = 15.sp,
        fontFamily = FontFamily(Font(R.font.dankmono)),
        color = Color.White
    )
}