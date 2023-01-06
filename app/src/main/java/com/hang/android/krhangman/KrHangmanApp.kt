package com.hang.android.krhangman

import android.app.Application
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.db.Repository

class KrHangmanApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
        HangmanApiFetchr.initialize()
    }
}