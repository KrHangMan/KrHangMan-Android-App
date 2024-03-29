package com.hang.android.krhangman.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hang.android.krhangman.Body
import com.hang.android.krhangman.BuildConfig
import com.hang.android.krhangman.Word
import com.hang.android.krhangman.WordBody
import com.hang.android.krhangman.model.User
import com.hang.android.krhangman.vm.LoginActivityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "HangmanApiFetchr"
private const val BASE_URL =BuildConfig.base_url

class HangmanApiFetchr {
    private val hangmanApi: HangmanApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        hangmanApi = retrofit.create(HangmanApi::class.java)
    }

    fun getRank(): MutableLiveData<List<Rank>> {
        val responseRankData: MutableLiveData<List<Rank>> = MutableLiveData()
        val rankRequest = hangmanApi.getRank()
        rankRequest.enqueue(object : Callback<RankResponse> {
            override fun onResponse(call: Call<RankResponse>, response: Response<RankResponse>) {
                Log.d(TAG, "response:${response.body()?.ranks} ${response.code()}")
                val rankResponse = response.body()
                responseRankData.value = rankResponse?.ranks
            }

            override fun onFailure(call: Call<RankResponse>, t: Throwable) {
                Log.e(TAG, "fail", t)
            }

        })

        return responseRankData
    }

    fun getMyRank(userName: String, setMyRank:(MyRank)-> Unit){
        val rankRequest = hangmanApi.getMyRank(userName)
        Log.d(TAG,"username:{$userName}")
        rankRequest.enqueue(object : Callback<MyRank> {
            override fun onResponse(call: Call<MyRank>, response: Response<MyRank>) {
                Log.d(TAG, "my rank response:${response.body()} ${response.code()}")

                if (response.code() == MY_RANK_NOT_EXIST) {
                    setMyRank(MyRank(userName, MY_RANK_NOT_EXIST))
                } else {
                    val rankResponse = response.body()
                    setMyRank(rankResponse!!)
                }

            }

            override fun onFailure(call: Call<MyRank>, t: Throwable) {
                Log.e(TAG, "fail", t)
            }

        })


    }


    fun addUser(userName: String, viewModel: LoginActivityViewModel) {
        val addUserRetrofit = hangmanApi.addUser(userName)

        addUserRetrofit.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    if (response.code() == EXIST_NAME) {
                        Log.e(TAG, "aleady exist nickname")
                        viewModel.addUserResponse.value = EXIST_NAME

                    } else if (response.code() == USER_INPUT_SUCCESS) {
                        Log.e(TAG, "addUser성공")
                        viewModel.userName = userName
                        viewModel.addUserResponse.value = USER_INPUT_SUCCESS
                    }
                } else {
                    Log.e(TAG, response.toString())

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
            }
        })
    }

    fun patchRank(user: User) {
        val service = hangmanApi.patchRank(user.nickname,user.score)

        service.enqueue(object : Callback<Body> {
            override fun onResponse(call: Call<Body>, response: Response<Body>) {
                Log.e("ERR", response.toString())
                if(response.isSuccessful){

                }
            }

            override fun onFailure(call: Call<Body>, t: Throwable) {

            }
        })
    }


    fun getAnswerList(wordList : MutableLiveData<ArrayList<Word>>) {
        val service = hangmanApi.getWord()

        service.enqueue(object : Callback<WordBody> {
            override fun onResponse(call: Call<WordBody>, response: Response<WordBody>) {
                if(response.isSuccessful){
                    wordList.value = response.body()!!.word as ArrayList<Word>
                    Log.e("FE", response.body()!!.toString())
                    //word update작업 필요 - GamActivity에서 observer 달아서 해주기
                }
            }

            override fun onFailure(call: Call<WordBody>, t: Throwable) {

            }
        })
    }


    companion object {
        private var INSTANCE: HangmanApiFetchr? = null
        const val EXIST_NAME = 203
        const val USER_INPUT_SUCCESS = 202
        const val GET_MY_RANK_SUCCESS = 200
        const val MY_RANK_NOT_EXIST = 404

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = HangmanApiFetchr()
            }
        }

        fun get(): HangmanApiFetchr {
            return INSTANCE ?: throw IllegalStateException("Retrofit must be initialize")
        }
    }


}