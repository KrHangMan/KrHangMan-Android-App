package com.hang.android.krhangman.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hang.android.krhangman.model.User

@Database(entities = [User::class], version = 1)
@TypeConverters(HangmanTypeConverter::class)

abstract class HangmanDB : RoomDatabase() {
    abstract fun hangmanDao(): HangmanDao
}