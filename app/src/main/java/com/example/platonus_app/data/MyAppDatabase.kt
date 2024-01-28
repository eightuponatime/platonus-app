package com.example.platonus_app.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, ScheduleEntity::class], version = 1)
abstract class MyAppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun scheduleDao(): ScheduleDao
}