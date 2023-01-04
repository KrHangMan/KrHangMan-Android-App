package com.hang.android.krhangman.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hang.android.krhangman.model.User

@Dao
interface UserDao{
        //user 가지고 오기
        @Query("SELECT * FROM User")
        suspend fun getUser(): User
        //user 넣기
        @Insert
        suspend fun addUser(user:User)
        //점수 넣기

        //점수 가지고 오기
}