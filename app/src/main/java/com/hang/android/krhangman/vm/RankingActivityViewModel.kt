package com.hang.android.krhangman.vm

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.api.Rank


class RankingActivityViewModel :BaseObservable() {

    @get:Bindable
    var rankList : LiveData<List<Rank>> = HangmanApiFetchr.get().getRank()


}