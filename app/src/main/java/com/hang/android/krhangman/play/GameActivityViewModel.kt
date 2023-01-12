package com.hang.android.krhangman.play

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hang.android.krhangman.Word
import com.hang.android.krhangman.api.HangmanApiFetchr

class GameActivityViewModel: ViewModel() {
    private val _answerList = MutableLiveData<ArrayList<Word>>()
    val answerList: MutableLiveData<ArrayList<Word>>
        get() = _answerList

    init {
        getAnswerList()
    }

    fun getAnswerList() {
        HangmanApiFetchr.get().getAnswerList(_answerList)
    }

}