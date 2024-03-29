package com.example.platonus_app.application.LoggedPage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.platonus_app.R
import com.example.platonus_app.application.StarterPage.GradientBackgroundBrush
import com.example.platonus_app.network.ApiClient.getAllUsersFromDatabase
import com.example.platonus_app.network.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListPage(
    navController: NavController,
    password: String,
    username: String,
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
                        var students by remember { mutableStateOf(emptyList<Student>()) }
                        var searchRequest by remember { mutableStateOf("") }

                        LaunchedEffect(Unit) {
                            val result = scope.async {
                                getAllUsersFromDatabase()
                            }

                            students = result.await()
                        }
                        Column() {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                                    .border(2.dp, Color.White),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                BasicTextField(
                                    value = searchRequest,
                                    onValueChange = {
                                        searchRequest = it
                                    },
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontFamily = FontFamily(Font(R.font.dankmono)),
                                    ),
                                    cursorBrush = SolidColor(Color.White),
                                    modifier = Modifier
                                        .height(50.dp)
                                        .background(Color.Transparent)
                                        .weight(1f)
                                        .border(2.dp, Color.White)
                                        .padding(5.dp, 8.dp)
                                )
                                IconButton(
                                    onClick = {
                                        //data base search realization
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search",
                                        tint = Color.White
                                    )
                                }
                            }

                            LazyColumn {
                                items(students) { student ->
                                    UserItem(student, navController, username, password, name, group)
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun UserItem(student: Student, navController: NavController, username: String, name: String, group: String, password: String) {
    //var photoBitmap: ImageBitmap? by remember { mutableStateOf(null) }

    /*LaunchedEffect(Unit) {
        val photoBytes = getImage(student.username)
        if (photoBytes.isNotEmpty()) {
            photoBitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.size).asImageBitmap()
        }
    }*/
    Card(
        colors = CardDefaults.cardColors(Color.Transparent),
        border = BorderStroke(1.dp, Color(0xff808080)),
        modifier = Modifier
            .padding(16.dp, 4.dp, 16.dp)
            .clickable {
                if(student.username != username) {
                    navController.navigate(
                        "user-page/$username/$password/$name/$group/${student.username}/${student.first_name}/${student.study_group}")
                } else if (student.username == username) {
                    navController.navigate("main/$username/$password/$name/$group")
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            /*if (photoBitmap != null) {
                Image(
                    bitmap = photoBitmap!!,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            }*/

            Spacer(modifier = Modifier.width(16.dp))
            Column() {
                Row() {
                    Text(
                        text = "Name: ${student.first_name} ",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.dankmono))
                    )
                    //Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${student.last_name}",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.dankmono))
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Group: ${student.study_group}",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.dankmono))
                )
            }
        }
    }
}
