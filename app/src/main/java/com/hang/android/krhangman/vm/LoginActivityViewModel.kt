package com.hang.android.krhangman.vm

import androidx.lifecycle.ViewModel
import com.hang.android.krhangman.db.Repository
import com.hang.android.krhangman.model.User

class LoginActivityViewModel : ViewModel(){
    private val repository=Repository.get()
    val userInfo=repository.getUser()

    fun addUser(user:User){
        repository.addUser(user)
    }
}