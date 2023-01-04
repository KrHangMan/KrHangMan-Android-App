package com.hang.android.krhangman.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hang.android.krhangman.R
import com.hang.android.krhangman.databinding.ActivityLoginBinding
import com.hang.android.krhangman.model.User
import com.hang.android.krhangman.vm.LoginActivityViewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginActivityViewModel by lazy{
        ViewModelProvider(owner = this@LoginActivity)[LoginActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityLoginBinding=DataBindingUtil.setContentView(this,
            R.layout.activity_login
        )

        binding.loginPageButtonSubmit.setOnClickListener {
            val nicknameInput=binding.loginPageNickname.text
            if(nicknameInput.isEmpty()){
               Toast.makeText(this@LoginActivity,getString(R.string.nickname_wrong_input),Toast.LENGTH_SHORT).show()
            }
            else{
                val user= User(nickname = nicknameInput.toString())
                viewModel.addUser(user)

                val intent = MainActivity.newIntent(this)
                startActivity(intent)
            }

        }
    }





    companion object{

        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }

    }


}