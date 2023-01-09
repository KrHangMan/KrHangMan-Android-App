package com.hang.android.krhangman.api

import com.google.gson.annotations.SerializedName

data class Rank (
    @SerializedName("rank") var rank:String,
    @SerializedName("username") var userName:String,
    @SerializedName("correct_answer") var correct_cnt:String
    )