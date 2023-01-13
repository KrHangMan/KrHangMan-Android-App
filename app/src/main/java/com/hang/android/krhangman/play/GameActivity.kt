package com.hang.android.krhangman

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.databinding.ActivityGameBinding
import com.hang.android.krhangman.db.DBRepository
import com.hang.android.krhangman.model.User
import com.hang.android.krhangman.play.GameActivityViewModel
import com.hang.android.krhangman.play.HelpDialog
import kotlin.collections.ArrayList


const val WORD_MAX_LENGTH = 5
const val MAX_ATTEMPT = 6

class GameActivity: AppCompatActivity() {

    private var attemptCnt = 0

    private lateinit var answerList: ArrayList<Word>
    private lateinit var answer: Word
    private var answerCnt = 0

    private val user = User(DBRepository.get().getUser().nickname)

    private lateinit var mBinding :ActivityGameBinding

    private val inputWords = Array(MAX_ATTEMPT) { InputWord() }
    private val adapter = WordRecyclerAdapter(inputWords)
    private val viewModel = GameActivityViewModel()

    private var isCalling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityGameBinding.inflate(layoutInflater).apply {
            viewWord.adapter = adapter
            viewWord.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

            txtHelp.setOnClickListener {
                HelpDialog(this@GameActivity).show()
            }

            imbBack.setOnClickListener {
                finish()
            }


            btnKeyEnter.setOnClickListener {
                if(answerList.isEmpty()){
                    Toast.makeText(applicationContext, "서버로 부터 값을 불러 오는 중입니다.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (isCalling) {
                    Toast.makeText(applicationContext, "서버로 부터 값을 불러 오는 중입니다.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if(inputWords[attemptCnt].chars.size != WORD_MAX_LENGTH) {
                    Toast.makeText(applicationContext, "입력 값을 확인 해 주세요.", Toast.LENGTH_SHORT).show()
                    clearUserInput()
                    return@setOnClickListener
                }

                Log.e("result", "${answer}\n${inputWords[attemptCnt]}")
                inputWords[attemptCnt].inOrder = createInorderList(inputWords[attemptCnt].chars)
                adapter.notifyItemChanged(attemptCnt)

                if(compareToAnswer(inputWords[attemptCnt].chars)) {
                    user.score++
                    GameResultDialog(this@GameActivity, true, answer, user).show()
                    clearAttempts()
                    return@setOnClickListener
                }

                if(attemptCnt + 1 >= MAX_ATTEMPT) {
                    GameResultDialog(this@GameActivity, false, answer, user).show()
                    clearAttempts()
                    return@setOnClickListener
                }
                attemptCnt++
            }
        }

        mBinding.lifecycleOwner = this
        mBinding.viewModel = viewModel

        viewModel.answerList.observe(this) {
            isCalling = false
            answerList = viewModel.answerList.value!!
            getNextWord()
        }
        setKeyboard()

        setContentView(mBinding.root)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        DBRepository.get().updateScore(user)
        HangmanApiFetchr.get().patchRank(user)
    }

    private fun getNextWord() {
        if(answerList.size <= answerCnt + 1) {
            isCalling = true
            answerCnt = 0
            viewModel.getAnswerList()
        }else {
            answer = answerList[answerCnt++]
        }
    }

    private fun clearUserInput() {
        inputWords[attemptCnt].chars.clear()
        adapter.notifyItemChanged(attemptCnt)
    }

    private fun clearAttempts() {
        attemptCnt = 0
        getNextWord()
        for(inputWord in inputWords) {
            inputWord.chars.clear()
            inputWord.inOrder.clear()
        }
        adapter.notifyDataSetChanged()
    }

    private fun setKeyboard() {
        var shiftPressed = false
        
        mBinding.apply {
            val btnKeys = arrayOf(btnKeyQ, btnKeyW, btnKeyE, btnKeyR, btnKeyT,
                btnKeyY, btnKeyU, btnKeyI, btnKeyO, btnKeyP, btnKeyA, btnKeyS,
                btnKeyD, btnKeyF, btnKeyG, btnKeyH, btnKeyJ, btnKeyK, btnKeyL,
                btnKeyZ, btnKeyX, btnKeyC, btnKeyV, btnKeyB, btnKeyN, btnKeyM)
    
            for(btn in btnKeys) {
                btn.setOnClickListener {
                    if(inputWords[attemptCnt].chars.size < WORD_MAX_LENGTH) {
                        inputWords[attemptCnt].chars += btn.text[0]
                        shiftPressed = false
                        switchShift(shiftPressed)
                        adapter.notifyItemChanged(attemptCnt)
                    }
                }
            }
    
            btnKeyShift.setOnClickListener {
                shiftPressed = !shiftPressed
                switchShift(shiftPressed)
            }
    
            btnKeyBack.setOnClickListener {
                try {
                    inputWords[attemptCnt].chars.removeLast()
                    adapter.notifyItemChanged(attemptCnt)
                } catch (_: Exception) {
    
                }
            }
        }
    }

    private fun switchShift(isPressed: Boolean) {
        if(isPressed) {
            mBinding.apply {
                btnKeyQ.text = "ㅃ"
                btnKeyW.text = "ㅉ"
                btnKeyE.text = "ㄸ"
                btnKeyR.text = "ㄲ"
                btnKeyT.text = "ㅆ"
                btnKeyO.text = "ㅒ"
                btnKeyP.text = "ㅖ"
            }
        }else {
            mBinding.apply {
                btnKeyQ.text = "ㅂ"
                btnKeyW.text = "ㅈ"
                btnKeyE.text = "ㄷ"
                btnKeyR.text = "ㄱ"
                btnKeyT.text = "ㅅ"
                btnKeyO.text = "ㅐ"
                btnKeyP.text = "ㅔ"
            }
        }
    }

    private fun compareToAnswer(list: ArrayList<Char>): Boolean {
        for(i in list.indices) {
            if(answer.spell[i] != list[i]) return false
        }
        return true
    }

    private fun createInorderList(inputWordList: ArrayList<Char>): ArrayList<Int> {
        return ArrayList<Int>().apply {
            for (i in answer.spell.indices) {
                when (inputWordList[i]) {
                    answer.spell[i] -> {
                        add(R.color.green)
                    }

                    in answer.spell -> {
                        add(R.color.yellow)
                    }

                    else -> {
                        add(R.color.light_gray)
                    }
                }
            }
        }
    }

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }
}
