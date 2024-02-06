package com.example.platonus_app.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/")
    suspend fun checkServerStatus(): String

    @GET("/getAllUsersFromDatabase")
    suspend fun getAllUsersFromDatabase(): List<Student>

    //parse the individual plan from the platonus
    @POST("/parse-plan")
    suspend fun parsePlan(@Body userRequest: UserRequest): String

    @POST("/insertPlan")
    suspend fun insertPlan(@Body insertPlanRequest: InsertPlanRequest): Boolean

    @POST("/insertSchedule")
    suspend fun insertSchedule(@Body insertScheduleRequest: InsertScheduleRequest): Boolean

    @POST("/addUser")
    suspend fun addUser(@Body studentRequest: StudentRequest): Boolean

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Boolean

    @POST("/parse-schedule")
    suspend fun parseSchedule(@Body parseRequest: ParseRequest): String

    @POST("/getPassword")
    suspend fun getPassword(@Body passwordRequest: PasswordRequest): String

    @POST("/checkIfUserExists")
    suspend fun checkIfUserExists(@Body existanceRequest: ExistanceRequest): Boolean

    @POST("/getSchedule")
    suspend fun getSchedule(@Body usernameRequest: UsernameRequest): String

    @POST("/getPlan")
    suspend fun getPlan(@Body usernameRequest: UsernameRequest): String

    @POST("/getGroup")
    suspend fun getGroup(@Body usernameRequest: UsernameRequest): String

    @POST("/getFirstName")
    suspend fun getFirstName(@Body usernameRequest: UsernameRequest): String

    @POST("/getLastName")
    suspend fun getLastName(@Body usernameRequest: UsernameRequest): String
}