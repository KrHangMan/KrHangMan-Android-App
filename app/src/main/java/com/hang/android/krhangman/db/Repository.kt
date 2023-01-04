package com.hang.android.krhangman.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.hang.android.krhangman.model.User
import kotlinx.coroutines.*

private const val DATABASE_NAME="hangman-database"

class Repository private constructor(context: Context){
    private val database:HangmanDB= Room.databaseBuilder(
        context.applicationContext,
        HangmanDB::class.java,
        DATABASE_NAME
    ).build()

    private val userDao=database.userDao()

    fun getUser(): User{
        var user:User
        runBlocking{
            user=userDao.getUser()
        }

        return user
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addUser(user:User){
        GlobalScope.launch(Dispatchers.IO){
            userDao.addUser(user)
        }
    }

    companion object{
        private var INSTANCE:Repository?=null

        fun initialize(context:Context){
            if(INSTANCE==null){
                INSTANCE=Repository(context)
            }
        }

        fun get():Repository{
            return INSTANCE?:throw IllegalStateException("Repository must be initialize")
        }
    }


}