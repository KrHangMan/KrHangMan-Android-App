package com.hang.android.krhangman.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


private const val TAG="HangmanApiFetchr"
private const val BASE_URL="http://ec2-13-125-198-195.ap-northeast-2.compute.amazonaws.com:80/"

class HangmanApiFetchr {
    private val hangmanApi:HangmanApi

    init {
        val retrofit: Retrofit =Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        hangmanApi=retrofit.create(HangmanApi::class.java)
    }

   /* fun getRank(){
        val test=hangmanApi.getRank()
        test.enqueue(object: Callback<RankResponse> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG,"response:${response.body()} ")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG,"fail",t)
            }

        })
    }*/

    fun addUser(userName:String){
        val test=hangmanApi.addUser(userName)

        test.enqueue(object:Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful.not()){
                    Log.e(TAG, response.toString())
                    return
                }else{
                    Log.e(TAG, "addUser성공")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
            }

        })
    }


    companion object{
        private var INSTANCE: HangmanApiFetchr?=null

        fun initialize(){
            if(INSTANCE==null){
                INSTANCE= HangmanApiFetchr()
            }
        }

        fun get(): HangmanApiFetchr {
            return INSTANCE?:throw IllegalStateException("Retrofit must be initialize")
        }
    }


}