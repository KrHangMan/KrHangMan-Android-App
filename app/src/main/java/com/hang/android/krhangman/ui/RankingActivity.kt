package com.hang.android.krhangman.ui

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hang.android.krhangman.R
import com.hang.android.krhangman.databinding.ActivityRankingBinding

class RankingActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityRankingBinding= DataBindingUtil.setContentView(this,
            R.layout.activity_ranking
        )
    }
}