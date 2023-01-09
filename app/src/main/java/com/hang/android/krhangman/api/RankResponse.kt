package com.hang.android.krhangman.api

import com.google.gson.annotations.SerializedName

class RankResponse {
    @SerializedName("add_rank")
    lateinit var ranks:List<Rank>
}