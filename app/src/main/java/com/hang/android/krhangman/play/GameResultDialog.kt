package com.hang.android.krhangman

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.databinding.DialGameResultBinding
import com.hang.android.krhangman.db.DBRepository
import com.hang.android.krhangman.model.User


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
                dismiss()
            }

            btnHome.setOnClickListener {
                DBRepository.get().updateScore(user)
                HangmanApiFetchr.get().patchRank(user)
                dismiss()
                (context as Activity).finish()
            }

        }

        setSize(
            width = (context.resources.displayMetrics.widthPixels * DIALOG_WIDTH).toInt(),
            height = (context.resources.displayMetrics.heightPixels * DIALOG_HEIGHT).toInt(),
        )
    }


}
