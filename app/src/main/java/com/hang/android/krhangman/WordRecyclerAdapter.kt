package com.hang.android.krhangman

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hang.android.krhangman.databinding.ListWordBinding

class WordRecyclerAdapter(data: ArrayList<InputWord>) :
    RecyclerView.Adapter<CustomViewHolder>() {
    private val mData: ArrayList<InputWord>
    private lateinit var context: Context

    init {
        mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        context = parent.context

        return CustomViewHolder(ListWordBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item: InputWord = mData[position]
        (holder.mBinding as ListWordBinding).apply {
            val texts = arrayOf(txt1, txt2, txt3, txt4, txt5)
            for(i in texts.indices) {
                texts[i].text = "${item.chars[i]}"
                texts[i].background.setColorFilter(context.getColor(item.inOrder[i]), PorterDuff.Mode.SRC_IN)
            }
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}
