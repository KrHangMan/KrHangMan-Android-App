package com.hang.android.krhangman

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hang.android.krhangman.api.Rank
import com.hang.android.krhangman.ui.RankingActivity

@BindingAdapter("setRankingItem")
fun setRankingItem(recycler:RecyclerView?,list:List<Rank>){
    (recycler?.adapter as? RankingActivity.RankAdapter)?.submitList(list)
}


