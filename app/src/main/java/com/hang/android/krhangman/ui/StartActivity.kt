package com.hang.android.krhangman.ui

import android.app.Activity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import com.hang.android.krhangman.R
import com.hang.android.krhangman.api.HangmanApi
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.databinding.ActivityStartBinding

const val INITIAL_LAUNCH="Initial Launch Check"

class StartActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        val binding:ActivityStartBinding= DataBindingUtil.setContentView(this,R.layout.activity_start)


        binding.apply {
            startPageText.apply{
                val anim=AnimationUtils.loadAnimation(context, R.anim.game_start_animation)
                startAnimation(anim)
            }
            startPage.apply{
                setOnClickListener {
                    val pref = getSharedPreferences(INITIAL_LAUNCH, MODE_PRIVATE)
                    val first = pref.getBoolean(INITIAL_LAUNCH, false)

                    if (!first) {
                        val intent = LoginActivity.newIntent(this@StartActivity)
                        startActivity(intent)
                    } else {
                        val intent = MainActivity.newIntent(this@StartActivity)
                        startActivity(intent)
                    }

                }
            }

        }




    }
}