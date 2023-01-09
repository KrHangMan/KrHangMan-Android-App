package com.hang.android.krhangman.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hang.android.krhangman.vm.LoginActivityViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


private const val TAG="HangmanApiFetchr"
private const val BASE_URL="http://ec2-13-125-198-195.ap-northeast-2.compute.amazonaws.com:80/"

class HangmanApiFetchr {
    private val hangmanApi:HangmanApi

    init {
        val retrofit: Retrofit =Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        hangmanApi=retrofit.create(HangmanApi::class.java)
    }
    fun test(){
        val rankRequest=hangmanApi.getTest()
        rankRequest.enqueue(object: Callback<RankResponse> {
            override fun onResponse(call: Call<RankResponse>, response: Response<RankResponse>) {
                Log.d(TAG,"response:$response ${response.body()?.ranks}")
            }

            override fun onFailure(call: Call<RankResponse>, t: Throwable) {
                Log.e(TAG,"fail",t)
            }

        })
    }
    fun getRank(): LiveData<List<Rank>> {
        val responseRankData:MutableLiveData<List<Rank>> =  MutableLiveData()
        val rankRequest=hangmanApi.getRank()
        rankRequest.enqueue(object: Callback<RankResponse> {
            override fun onResponse(call: Call<RankResponse>, response: Response<RankResponse>) {
                Log.d(TAG,"response:${response.body()?.ranks} ${response.code()}")
                val rankResponse=response.body()
                responseRankData.value=rankResponse?.ranks
            }

            override fun onFailure(call: Call<RankResponse>, t: Throwable) {
                Log.e(TAG,"fail",t)
            }

        })
        Log.d("beforeReturn",responseRankData.value.toString())
        return responseRankData
    }

   fun addUser(userName:String,viewModel:LoginActivityViewModel){
        val addUserRetrofit=hangmanApi.addUser(userName)

        addUserRetrofit.enqueue(object:Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    if(response.code()==EXIST_NAME){
                        Log.e(TAG, "aleady exist nickname")
                        viewModel.addUserResponse.value= EXIST_NAME

                    }else if(response.code()== USER_INPUT_SUCCESS){
                        Log.e(TAG, "addUser성공")
                        viewModel.userName=userName
                        viewModel.addUserResponse.value= USER_INPUT_SUCCESS
                    }
                }else {
                    Log.e(TAG, response.toString())

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
            }
        })
    }



    companion object{
        private var INSTANCE: HangmanApiFetchr?=null
        const val EXIST_NAME=203
        const val USER_INPUT_SUCCESS=202

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