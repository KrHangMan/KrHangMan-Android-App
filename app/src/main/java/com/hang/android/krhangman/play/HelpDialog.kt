package com.hang.android.krhangman.play

import android.content.Context
import android.view.LayoutInflater
import com.hang.android.krhangman.CustomDialog
import com.hang.android.krhangman.DIALOG_HEIGHT
import com.hang.android.krhangman.DIALOG_WIDTH
import com.hang.android.krhangman.databinding.DialHelpBinding

class HelpDialog(context: Context): CustomDialog(context) {
    init {
        mBinding = DialHelpBinding.inflate(LayoutInflater.from(context)).apply {
            btnExit.setOnClickListener {
                dismiss()
            }
        }

        setSize(
            width = (context.resources.displayMetrics.widthPixels * DIALOG_WIDTH).toInt(),
            height = (context.resources.displayMetrics.heightPixels * DIALOG_HEIGHT).toInt(),
        )
    }
}