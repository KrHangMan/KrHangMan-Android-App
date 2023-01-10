package com.hang.android.krhangman.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.hang.android.krhangman.GameActivity
import com.hang.android.krhangman.R
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.databinding.ActivityMainBinding
import com.hang.android.krhangman.db.Repository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


const val TAG = "Main Activity"

class MainActivity : Activity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        Log.d(TAG, "test")
        GlobalScope.launch {
            Log.d(TAG, Repository.get().getUser().toString())
        }

        binding.mainPageButtonRank.setOnClickListener {
            val intent = RankingActivity.newIntent(this@MainActivity)
            startActivity(intent)
        }
        binding.mainPageButtonGameStart.setOnClickListener {
            val intent = GameActivity.newIntent(this@MainActivity)
            startActivity(intent)

        }


    }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}