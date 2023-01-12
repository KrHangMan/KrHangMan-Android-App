package com.hang.android.krhangman

import com.google.gson.annotations.SerializedName

data class Word(
    @SerializedName("word")
    val word: String,

    @SerializedName("mean")
    val mean: String,

    @SerializedName("spell")
    val spell: List<Char>
)

data class WordBody(
    @SerializedName("word_list")
    val word: List<Word>
)

data class Body(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("url")
    val url: String
)

data class InputWord(val chars: ArrayList<Char> = ArrayList(), var inOrder: ArrayList<Int> = ArrayList())