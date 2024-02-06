package com.example.platonus_app.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.sql.Blob
import java.util.concurrent.TimeUnit

object ApiClient {
    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(290, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        //.baseUrl("http://192.168.236.125:8080/")
        .baseUrl("http://192.168.1.5:8080/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    suspend fun sendUserDataToServer(username: String, password: String, first_name: String,
                                     last_name: String, study_group: String): Boolean {
        val studentReq = StudentRequest(username, password, first_name, last_name, study_group)
        val gson = Gson()
        val userJson = gson.toJson(studentReq)

        return apiService.addUser(studentReq)
    }


    suspend fun logInDatabase(username: String, password: String): Boolean {
        val loginReq = LoginRequest(username, password)

        return apiService.login(loginReq)
    }

    suspend fun scheduleParser(username: String, password: String): String {
        val parseReq = ParseRequest(username, password)

        return apiService.parseSchedule(parseReq)
    }

    suspend fun parseIndividualPlan(username: String, password: String): String {
        val userReq = UserRequest(username, password)

        return apiService.parsePlan(userReq)
    }

    suspend fun insertIndividualPlan(username: String, plan: String): Boolean {
        val insertPlanRequest = InsertPlanRequest(username, plan)

        return apiService.insertPlan(insertPlanRequest)
    }

    suspend fun insertScheduleInDatabase(username: String, schedule: String): Boolean {
        val insertScheduleRequest = InsertScheduleRequest(username, schedule)

        return apiService.insertSchedule(insertScheduleRequest)
    }

    suspend fun getAllUsersFromDatabase(): List<Student> {
        return apiService.getAllUsersFromDatabase()
    }

    suspend fun getPassword(username: String): String {
        val pswdRequest = PasswordRequest(username)

        return try {
            val password = apiService.getPassword(pswdRequest)
            password
        } catch (e: Exception) {
            println("Error getting password: ${e.message}")
            ""
        }
    }

    suspend fun checkIfUserExists(username: String): Boolean {
        val existanceRequest = ExistanceRequest(username)

        return apiService.checkIfUserExists(existanceRequest)
    }

    suspend fun getSchedule(username: String): String {
        val scheduleRequest = UsernameRequest(username)

        return try {
            val schedule = apiService.getSchedule(scheduleRequest)
            schedule
        } catch(e: Exception) {
            println("Error getting schedule: ${e.message}")
            ""
        }
    }

    suspend fun getPlan(username: String): String {
        val planRequest = UsernameRequest(username)

        return try {
            val plan = apiService.getPlan(planRequest)
            plan
        } catch(e: Exception) {
            println("Error getting schedule: ${e.message}")
            ""
        }
    }

    suspend fun getGroup(username: String): String {
        val groupRequest = UsernameRequest(username)

        return try {
            val group = apiService.getGroup(groupRequest)
            group
        } catch (e: Exception) {
            println("Error getting group: ${e.message}")
            ""
        }
    }

    suspend fun getFirstName(username: String): String {
        val nameRequest = UsernameRequest(username)

        return try {
            val name = apiService.getFirstName(nameRequest)
            name
        } catch (e: Exception) {
            println("Error getting name: ${e.message}")
            ""
        }
    }

    suspend fun getLastName(username: String): String {
        val surnameRequest = UsernameRequest(username)

        return try {
            val surname = apiService.getLastName(surnameRequest)
            surname
        } catch (e: Exception) {
            println("Error getting surname: ${e.message}")
            ""
        }
    }
}