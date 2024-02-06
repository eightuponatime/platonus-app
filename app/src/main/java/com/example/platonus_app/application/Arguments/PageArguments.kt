package com.example.platonus_app.application.Arguments

data class MainPageArguments(
    val username: String,
    val password: String,
    val name: String,
    val group: String
)

data class OfflineScheduleArguments(
    val username: String
)

data class SchedulePageArguments(
    val username: String,
    val password: String,
    val name: String,
    val group: String
)

data class PlanPageArguments(
    val username: String,
    val password: String,
    val name: String,
    val group: String
)

data class UserListPageArguments(
    val username: String,
    val password: String,
    val name: String,
    val group: String
)

data class UserPageArguments(
    val username: String,
    val password: String,
    val name: String,
    val group: String,
    val user_username: String,
    val user_name: String,
    val user_group: String
)

data class UserSchedulePageArguments(
    val username: String,
    val password: String,
    val name: String,
    val group: String,
    val user_username: String,
    val user_name: String,
    val user_group: String
)