package com.hang.android.krhangman.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hang.android.krhangman.R
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.databinding.ActivityLoginBinding
import com.hang.android.krhangman.model.User
import com.hang.android.krhangman.vm.LoginActivityViewModel
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginActivityViewModel by lazy {
        ViewModelProvider(owner = this@LoginActivity)[LoginActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )

        binding.loginPageEditTextNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length > 20) {
                    binding.loginPageEditTextNickname.error =
                        getString(R.string.nickname_wrong_input_long)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.loginPageButtonSubmit.setOnClickListener {
            val nicknameInput = binding.loginPageEditTextNickname.text.toString()

            val isValidateNickname = checkNicknameValidate(nicknameInput)
            if (isValidateNickname) {
                val user = User(nickname = nicknameInput)
                viewModel.requestAddUser(user)
            }


        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        viewModel.addUserResponse.observe(
            this,
            Observer { value ->
                if (value == HangmanApiFetchr.USER_INPUT_SUCCESS) {
                    viewModel.addUser()
                    val pref = getSharedPreferences(INITIAL_LAUNCH, MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putBoolean(INITIAL_LAUNCH, true)
                    editor.apply()

                    val intent = MainActivity.newIntent(this)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        baseContext,
                        getString(R.string.nickname_already_exist),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )




        return super.onCreateView(name, context, attrs)
    }

    private fun checkNicknameValidate(nickname: String): Boolean {
        /*
            2글자 이상 20글자미만, 한글 영어 숫자, 특수문자 사용 x

         */

        if (nickname.length < 2) {
            Toast.makeText(baseContext, R.string.nickname_wrong_input_short, Toast.LENGTH_SHORT)
                .show()
            return false
        }

        if (nickname.length > 20) {
            Toast.makeText(baseContext, R.string.nickname_wrong_input_long, Toast.LENGTH_SHORT)
                .show()

            return false
        }

        val pattern = "^[ㄱ-ㅣ가-힣a-zA-Z0-9\\s]+$"
        val p = Pattern.matches(pattern, nickname)
        if (!p) {
            Toast.makeText(baseContext, R.string.nickname_wrong_input_special, Toast.LENGTH_SHORT)
                .show()

            return false
        }

        return true
    }


    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }

    }


}