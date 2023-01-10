package com.hang.android.krhangman

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import com.hang.android.krhangman.databinding.DialGameResultBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val DIALOG_WIDTH = 0.8
const val DIALOG_HEIGHT = 0.4

class GameResultDialog(context: Context, isCorrect: Boolean, word: Word, user: User): CustomDialog(context) {

    init {
        mBinding = DialGameResultBinding.inflate(LayoutInflater.from(context)).apply {
            if(isCorrect) {
                txtGameResult.text = "정답 입니다. ${word.word}\n뜻 : ${word.mean}"
                btnContinue.text = "계속 하기"
            }else {
                txtGameResult.text = "아쉽지만, 정답은 '${word.word}' 입니다.\n뜻 : ${word.mean}"
                btnContinue.text = "다시 하기"
            }
            btnContinue.setOnClickListener {
                patchRank(user)
                dismiss()
            }
            btnHome.setOnClickListener {
                dismiss()
                (context as Activity).finish()
            }
        }

        setSize(
            width = (context.resources.displayMetrics.widthPixels * DIALOG_WIDTH).toInt(),
            height = (context.resources.displayMetrics.heightPixels * DIALOG_HEIGHT).toInt(),
        )
    }

    private fun patchRank(user: User) {
        Log.e("ERR", user.toString())
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-13-125-198-195.ap-northeast-2.compute.amazonaws.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service: RetrofitService = retrofit.create(RetrofitService::class.java)

        service.patchRank(user.name, user.score).enqueue(object : Callback<Body> {
            override fun onResponse(call: Call<Body>, response: Response<Body>) {
                Log.e("ERR", response.toString())
                if(response.isSuccessful){

                }
            }

            override fun onFailure(call: Call<Body>, t: Throwable) {

            }
        })
    }
}
