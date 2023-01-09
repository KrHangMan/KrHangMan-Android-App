package com.hang.android.krhangman.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HangmanApi {


    //user 넣기
    @POST("/api/users")
    @FormUrlEncoded
    fun addUser(@Field("username") user_name: String): Call<Void>

    //전체 rank 조회하기
    @GET("/api/users/rank/")
    fun getRank(): Call<RankResponse>

    //내 rank 조회하기
    @GET("/api/users/rank/{user_name}")
    fun getMyRank(
        @Path("user_name") user_name: String
    ): Call<MyRank>

    @GET("/api/users/rank/")
    fun getTest(): Call<RankResponse>

}