package com.example.platonus_app.application.StarterPage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import com.example.platonus_app.R
import com.example.platonus_app.data.DatabaseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineSchedule(
    navController: NavController,
    username: String,
) {

    val scope = rememberCoroutineScope()
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
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
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
                var result by remember {
                    mutableStateOf("")
                }

                LaunchedEffect(username) {
                    val schedule = withContext(Dispatchers.IO) {
                        val scheduleDao = DatabaseManager.getDatabase().scheduleDao()
                        val scheduleEntity = scheduleDao.getScheduleByUsername(username)
                        scheduleEntity?.schedule
                    }
                    if (schedule != null) {
                        result = schedule
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
                                                modifier = Modifier.padding(16.dp)
                                            )
                                        }
                                        Text(
                                            text = dayString.trim(),
                                            color = Color.White,
                                            fontFamily = FontFamily(Font(R.font.dankmono)),
                                            modifier = Modifier.padding(16.dp)
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