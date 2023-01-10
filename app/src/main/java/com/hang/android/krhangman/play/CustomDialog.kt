package com.hang.android.krhangman

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.viewbinding.ViewBinding

abstract class CustomDialog(context: Context) {
    private val dial = Dialog(context)
    lateinit var mBinding: ViewBinding
    private var width = ActionBar.LayoutParams.MATCH_PARENT
    private var height = ActionBar.LayoutParams.MATCH_PARENT

    init {
        dial.requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    fun show() {
        initDial()
        dial.show()
    }

    private fun initDial() {
        dial.setContentView(mBinding.root)
        setSize(width, height)
        dial.window?.setLayout(width, height)
    }

    fun setSize(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    fun dismiss() {
        dial.dismiss()
    }
}
