package com.hang.android.krhangman

import android.content.Context
import android.graphics.PorterDuff
import android.renderscript.ScriptGroup.Input
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hang.android.krhangman.databinding.ListWordBinding

class WordRecyclerAdapter(val data: Array<InputWord>) :
    RecyclerView.Adapter<CustomViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        context = parent.context

        return CustomViewHolder(ListWordBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item: InputWord = data[position]
        (holder.mBinding as ListWordBinding).apply {
            val texts = arrayOf(txt1, txt2, txt3, txt4, txt5)

            for(text in texts) {
                text.text = ""
                text.background.colorFilter = null
            }

            for(i in item.chars.indices) {
                texts[i].text = "${item.chars[i]}"
            }
            for(i in item.inOrder.indices) {
                texts[i].background.setColorFilter(context.getColor(item.inOrder[i]), PorterDuff.Mode.SRC_IN)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
