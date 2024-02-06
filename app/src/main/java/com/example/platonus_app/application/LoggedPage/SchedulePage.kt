package com.example.platonus_app.application.LoggedPage

import org.json.JSONObject
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.platonus_app.R
import com.example.platonus_app.data.DatabaseManager
import com.example.platonus_app.data.ScheduleEntity
import com.example.platonus_app.network.ApiClient
import com.example.platonus_app.network.ApiClient.scheduleParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchedulePage(
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
                Icons.Default.Face, "user list", onClick =
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
                        var result by remember {
                            mutableStateOf("")
                        }
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                        ) {
                            Row{
                                Button(
                                    onClick = {
                                        result = "parsing result waiting room..."
                                        CoroutineScope(Dispatchers.IO).launch {

                                            try {
                                                val resultJson = scheduleParser(username, password)
                                                val json = JSONObject(resultJson)
                                                result = json.getString("result")
                                                if (result.startsWith("Понедельник")) {
                                                    val scheduleDao = DatabaseManager.getDatabase().scheduleDao()
                                                    ApiClient.insertScheduleInDatabase(username, result)
                                                    val existingSchedule = scheduleDao.getScheduleByUsername(username)

                                                    if (existingSchedule != null) {
                                                        existingSchedule.schedule = result
                                                        scheduleDao.update(existingSchedule)
                                                    } else {
                                                        val scheduleEntity = ScheduleEntity(username = username, schedule = result)
                                                        scheduleDao.insert(scheduleEntity)
                                                    }
                                                }

                                            } catch (e: Exception) {
                                                println("")
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                                    border = BorderStroke(width = 1.dp, color = Color(0xff808080))
                                ) {
                                    Text(
                                        text = "parse schedule",
                                        fontFamily = FontFamily(Font(R.font.dankmono)),
                                        color = Color.White
                                    )
                                }


                                Spacer(Modifier.width(8.dp))
                                var emptySchedule by remember {
                                    mutableStateOf(false)
                                }
                                Button(
                                    onClick = {
                                        CoroutineScope(Dispatchers.IO).launch {

                                            val scheduleDao = DatabaseManager.getDatabase().scheduleDao()
                                            val scheduleEntity = scheduleDao.getScheduleByUsername(
                                                username
                                            )

                                            if (scheduleEntity != null) {
                                                val schedule = scheduleEntity.schedule
                                                result = schedule

                                            } else {
                                                emptySchedule = true
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                                    border = BorderStroke(width = 1.dp, color = Color(0xff808080))
                                ) {
                                    Text(
                                        text = "saved schedule",
                                        fontFamily = FontFamily(Font(R.font.dankmono)),
                                        color = Color.White
                                    )
                                }
                                if(emptySchedule) {
                                    EmptyScheduleAlertDialog {
                                        emptySchedule = false
                                    }
                                }

                            }

                            LazyColumn(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            ) {
                                if (result.isNotEmpty()) {
                                    val dayStrings = result.split(
                                        "Вторник", "Среда", "Четверг",
                                        "Пятница", "Суббота", "Воскресенье")

                                    val daysOfWeek = listOf("Понедельник", "Вторник",
                                        "Среда", "Четверг",
                                        "Пятница", "Суббота",
                                        "Воскресенье")

                                    for ((index, dayString) in dayStrings.withIndex()) {
                                        item {
                                            Card(
                                                colors = CardDefaults.cardColors(Color.Transparent),
                                                border = BorderStroke(1.dp, Color(0xff808080)),
                                                modifier = Modifier.fillMaxWidth()
                                                    .padding(0.dp,0.dp,0.dp,8.dp)
                                            ) {
                                                Column {
                                                    if(index > 0) {
                                                        Text(
                                                            text = daysOfWeek[index],
                                                            color = Color.White,
                                                            fontFamily = FontFamily(Font(R.font.dankmono)),
                                                            modifier = Modifier.padding(15.dp)
                                                        )
                                                    }
                                                    Text(
                                                        text = dayString.trim(),
                                                        color = Color.White,
                                                        fontFamily = FontFamily(Font(R.font.dankmono)),
                                                        modifier = Modifier.padding(15.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    item {
                                        Text(
                                            text = result,
                                            color = Color.White,
                                            fontFamily = FontFamily(Font(R.font.dankmono)),
                                        )
                                    }
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
fun EmptyScheduleAlertDialog(onClose: () -> Unit) {
    AlertDialog(
        containerColor = Color.Black,
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
                text = "there're no schedules for this user",
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