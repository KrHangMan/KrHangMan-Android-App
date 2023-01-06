package com.hang.android.krhangman.api

import android.content.Context
import android.util.Log
import com.hang.android.krhangman.db.Repository
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
        val retrofit: Retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        hangmanApi=retrofit.create(HangmanApi::class.java)
    }

    fun getRank(){
        val test=hangmanApi.fetchContents()
        test.enqueue(object:Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG,"response:${response.body()} ")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG,"fail",t)
            }

        })
    }
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

   /* fun fetchPhotos(): LiveData<List<Rank>> {
        val responseLiveData:MutableLiveData<List<GalleryItem>> = MutableLiveData()
        val flickrRequest: Call<PhotoResponse> =flickrApi.fetchPhotos()

        flickrRequest.enqueue(object : Callback<PhotoResponse>{
            override fun onResponse(call: Call<PhotoResponse>, response: Response<PhotoResponse>) {
                Log.d(TAG,"Response received")
                val photoResponse=response.body()
                var galleryItem=photoResponse?.galleryItem?.filterNot{it.url.isBlank()}
                responseLiveData.value=galleryItem
            }

            override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                Log.d(TAG,"Failed to fetch photos",t)
            }

        })
        return responseLiveData
    }
*/

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