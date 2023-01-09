package com.hang.android.krhangman.api

import com.google.gson.annotations.SerializedName

data class MyRank(
    @SerializedName("username") val userName:String,
    @SerializedName("ranking") val rank:Int
)