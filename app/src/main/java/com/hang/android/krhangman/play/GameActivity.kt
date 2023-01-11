package com.hang.android.krhangman

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hang.android.krhangman.api.HangmanApiFetchr
import com.hang.android.krhangman.databinding.ActivityGameBinding
import com.hang.android.krhangman.db.Repository
import com.hang.android.krhangman.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList


const val WORD_MAX_LENGTH = 5
const val MAX_ATTEMPT = 5

class GameActivity: AppCompatActivity() {

    private var attemptCnt = 0

    private val wordList: ArrayList<Word> = ArrayList()
    private lateinit var word: Word
    private var wordNum = 0

    private val user = User(Repository.get().getUser().nickname, 0)

    private lateinit var mBinding :ActivityGameBinding

    private val inputWords = ArrayList<InputWord>()
    private val adapter = WordRecyclerAdapter(inputWords)

    private val inputWord = ArrayList<Char>()
    private lateinit var typedChar: Array<TextView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getNextWord()

        mBinding = ActivityGameBinding.inflate(layoutInflater).apply {
            viewWord.adapter = adapter
            viewWord.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            txtAttempts.text = "남은 시도 수 : ${MAX_ATTEMPT}"

            typedChar = arrayOf(txt1, txt2, txt3, txt4, txt5)

            imbBack.setOnClickListener {
                finish()
            }

            btnKeyEnter.setOnClickListener {
                if(wordList.isEmpty()){
                    Toast.makeText(applicationContext, "서버로 부터 값을 불러 오는 중입니다.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if(inputWord.size != WORD_MAX_LENGTH) {
                    Toast.makeText(applicationContext, "입력 값을 확인 해 주세요.", Toast.LENGTH_SHORT).show()
                    clearUserInput()
                    return@setOnClickListener
                }

                Log.e("ERR", "${word}\n${inputWord}")
                updateUserInput()

                if(compareToAnswer(inputWord)) {
                    user.score++
                    GameResultDialog(this@GameActivity, true, word, user).show()
                    clearAttempts()
                }

                if(attemptCnt >= MAX_ATTEMPT) {
                    GameResultDialog(this@GameActivity, false, word, user).show()
                    clearAttempts()
                }

                clearUserInput()
            }
        }
        
        setKeyboard()

        setContentView(mBinding.root)
    }

    private fun getNextWord() {
        if(wordList.size <= wordNum + 1) {
            HangmanApiFetchr.get().getWordList(wordList) //wordList를 LiveData로 바꿔서 전달하기
        }else {
            word = wordList[wordNum++]
        }
    }



    private fun updateUserInput() {
        val copyList = inputWord.clone() as ArrayList<Char>
        inputWords += InputWord(copyList, createInorderList(copyList))
        attemptCnt++
        updateAttemptsText(MAX_ATTEMPT - attemptCnt)
        adapter.notifyItemInserted(inputWords.size)
    }

    private fun clearUserInput() {
        inputWord.clear()
        for(txt in typedChar) {
            txt.text = ""
        }
    }

    private fun clearAttempts() {
        attemptCnt = 0
        updateAttemptsText(MAX_ATTEMPT)
        getNextWord()
        inputWords.clear()
        adapter.notifyDataSetChanged()
    }

    private fun updateAttemptsText(num: Int) {
        val attemptTxt = "남은 시도 수 : ${num}"
        mBinding.txtAttempts.text = attemptTxt
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
                    if(inputWord.size < WORD_MAX_LENGTH) {
                        inputWord += btn.text[0]
                        typedChar[inputWord.lastIndex].text = btn.text
                        shiftPressed = false
                        switchShift(shiftPressed)
                    }
                }
            }
    
            btnKeyShift.setOnClickListener {
                shiftPressed = !shiftPressed
                switchShift(shiftPressed)
            }
    
            btnKeyBack.setOnClickListener {
                try {
                    typedChar[inputWord.lastIndex].text = ""
                    inputWord.removeLast()
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
            if(word.spell[i] != list[i]) return false
        }
        return true
    }

    private fun createInorderList(inputWordList: ArrayList<Char>): ArrayList<Int> {
        return ArrayList<Int>().apply {
            for (i in word.spell.indices) {
                when (inputWordList[i]) {
                    word.spell[i] -> {
                        add(R.color.green)
                    }

                    in word.spell -> {
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
