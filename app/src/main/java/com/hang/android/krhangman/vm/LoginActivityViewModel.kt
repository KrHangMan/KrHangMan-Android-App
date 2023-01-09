package com.hang.android.krhangman.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hang.android.krhangman.api.HangmanApi
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.db.Repository
import com.hang.android.krhangman.model.User

class LoginActivityViewModel : ViewModel(){
    val repository=Repository.get()
    private val api=HangmanApiFetchr.get()
    var addUserResponse: MutableLiveData<Int> = MutableLiveData()
    var userName=""

    fun requestAddUser(user:User){
        api.addUser(user.nickname,this)
    }
    fun addUser(){
        repository.addUser(User(nickname=userName))
    }

}