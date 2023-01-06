package com.hang.android.krhangman.api

import com.google.gson.annotations.SerializedName

class RankResponse {
    @SerializedName("rank")
    lateinit var ranks:List<Rank>
}