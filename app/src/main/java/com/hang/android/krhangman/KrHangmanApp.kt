package com.hang.android.krhangman

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.db.DBRepository

class KrHangmanApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        DBRepository.initialize(this)
        HangmanApiFetchr.initialize()
    }
}