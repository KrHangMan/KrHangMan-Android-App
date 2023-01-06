package com.hang.android.krhangman.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hang.android.krhangman.R
import com.hang.android.krhangman.api.Rank
import com.hang.android.krhangman.databinding.ActivityRankingBinding
import com.hang.android.krhangman.databinding.ItemRankRecyclerBinding

class RankingActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityRankingBinding= DataBindingUtil.setContentView(this,
            R.layout.activity_ranking
        )
        binding.rankPageRecyclerView.adapter=RankAdapter(diffUtil)
    }


    private inner class RankHolder(private val binding: ItemRankRecyclerBinding):
        RecyclerView.ViewHolder(binding.root){

            fun bind(rank:Rank){
                binding.itemRankName.text=rank.userName
                binding.itemRankScore.text=rank.userName
            }


    }

    private inner class RankAdapter(diffUtil: DiffUtil.ItemCallback<Rank>): ListAdapter<Rank,RankHolder>(diffUtil){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankHolder {
            val binding = ItemRankRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return RankHolder(binding)
        }

        override fun onBindViewHolder(holder: RankHolder, position: Int) {
            holder.bind(getItem(position))
        }

    }





    companion object {

        private const val TAG = "RankingActivity"


        private val diffUtil = object : DiffUtil.ItemCallback<Rank>() {
            override fun areItemsTheSame(oldItem: Rank, newItem: Rank): Boolean {
                return oldItem.userName == newItem.userName
            }

            override fun areContentsTheSame(oldItem: Rank, newItem: Rank): Boolean {
                return oldItem == newItem
            }
        }
    }

}