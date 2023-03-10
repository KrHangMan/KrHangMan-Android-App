package com.hang.android.krhangman.db

import android.content.Context
import androidx.room.Room
import com.hang.android.krhangman.model.User
import kotlinx.coroutines.*

private const val DATABASE_NAME = "hangman-database"

class DBRepository private constructor(context: Context) {
    private val database: HangmanDB = Room.databaseBuilder(
        context.applicationContext,
        HangmanDB::class.java,
        DATABASE_NAME
    ).build()

    private val hangmanDao = database.hangmanDao()

    fun getUser(): User {
        var user: User
        runBlocking {
            user = hangmanDao.getUser()
        }

        return user
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addUser(user: User) {
        GlobalScope.launch(Dispatchers.IO) {
            hangmanDao.addUser(user)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun updateScore(user:User){
        GlobalScope.launch(Dispatchers.IO) {
            hangmanDao.updateScore(user)
        }
    }

    companion object {
        private var INSTANCE: DBRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = DBRepository(context)
            }
        }

        fun get(): DBRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialize")
        }
    }


}