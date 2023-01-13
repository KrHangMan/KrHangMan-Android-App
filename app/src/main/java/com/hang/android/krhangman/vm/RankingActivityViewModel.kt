package com.hang.android.krhangman.vm

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.api.MyRank
import com.hang.android.krhangman.api.Rank
import com.hang.android.krhangman.db.DBRepository


class RankingActivityViewModel : ViewModel() {
    val fireEmoji=String(Character.toChars(0x1F525))
    val trophyEmoji=String(Character.toChars(0x1F3C6))

    var rankList: MutableLiveData<List<Rank>> =MutableLiveData(emptyList())


    var myRank: MutableLiveData<MyRank> = MutableLiveData()
    set(value){
        field = value
        myRank.postValue(value.value)
    }


    init {
        myRank=HangmanApiFetchr.get().getMyRank(DBRepository.get().getUser().nickname)
        rankList=HangmanApiFetchr.get().getRank()
    }

}


