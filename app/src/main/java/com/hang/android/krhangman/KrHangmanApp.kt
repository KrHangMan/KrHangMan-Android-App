package com.hang.android.krhangman

import android.app.Application
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.db.DBRepository

class KrHangmanApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DBRepository.initialize(this)
        HangmanApiFetchr.initialize()
    }
}