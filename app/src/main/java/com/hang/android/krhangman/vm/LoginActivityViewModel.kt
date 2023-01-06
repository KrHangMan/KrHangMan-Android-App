package com.hang.android.krhangman.vm

import androidx.lifecycle.ViewModel
import com.hang.android.krhangman.api.HangmanApi
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.db.Repository
import com.hang.android.krhangman.model.User

class LoginActivityViewModel : ViewModel(){
    private val repository=Repository.get()
    private val api=HangmanApiFetchr.get()
    val userInfo=repository.getUser()

    fun addUser(user:User){
        repository.addUser(user)
        api.addUser(user.nickname)
    }

}