package com.hang.android.krhangman

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface RetrofitService {

    @GET("words/")
    fun getWord(): Call<WordBody>

    @FormUrlEncoded
    @PATCH("users/{userName}")
    fun patchRank(
        @Path("userName") userName: String,
        @Field("correct_cnt") cnt: Int): Call<Body>
}
