package com.hang.android.krhangman

import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter

@BindingAdapter("loadGuidePage")
fun loadGuidePage(imgView:ImageView,img:Int){
    imgView.setImageResource(img)
    Log.d("bind",img.toString())
}