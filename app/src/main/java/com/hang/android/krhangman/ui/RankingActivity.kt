package com.hang.android.krhangman.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hang.android.krhangman.R
import com.hang.android.krhangman.api.Rank
import com.hang.android.krhangman.databinding.ActivityRankingBinding
import com.hang.android.krhangman.databinding.ItemRankRecyclerBinding
import com.hang.android.krhangman.vm.RankingActivityViewModel

class RankingActivity : AppCompatActivity(), LifecycleOwner {
    private val viewModel = RankingActivityViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityRankingBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_ranking
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner=this@RankingActivity


        binding.rankPageRecyclerView.adapter =
            RankAdapter(diffUtil).apply { submitList(viewModel.rankList.value) }

        viewModel.apply {
            rankList.observe(
                this@RankingActivity,
                Observer {
                    Log.d("rankObserver", it.toString())
                    (binding.rankPageRecyclerView.adapter as RankAdapter).submitList(it)
                }
            )

        }
    }


    inner class RankHolder(private val binding: ItemRankRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rank: Rank) {
            binding.rank = rank
        }


    }

    inner class RankAdapter(diffUtil: DiffUtil.ItemCallback<Rank>) :
        ListAdapter<Rank, RankHolder>(diffUtil) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankHolder {
            val binding = ItemRankRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return RankHolder(binding)
        }

        override fun onBindViewHolder(holder: RankHolder, position: Int) {
            Log.d(TAG, "onBindViewHolder ${getItem(position)}")
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

        fun newIntent(context: Context): Intent {
            return Intent(context, RankingActivity::class.java)
        }
    }

}