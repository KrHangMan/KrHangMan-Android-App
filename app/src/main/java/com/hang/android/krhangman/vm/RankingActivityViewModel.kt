package com.hang.android.krhangman.vm

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.api.MyRank
import com.hang.android.krhangman.api.Rank
import com.hang.android.krhangman.db.Repository


class RankingActivityViewModel : ViewModel() {

    var rankList: LiveData<List<Rank>> = HangmanApiFetchr.get().getRank()
    var myRank: LiveData<MyRank> =
        HangmanApiFetchr.get().getMyRank(Repository.get().getUser().nickname)

}