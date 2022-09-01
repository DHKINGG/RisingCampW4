package com.example.risingcampw4.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.risingcampw4.Model.ActivityViewModel
import com.example.risingcampw4.Model.FragmentViewModel
import com.example.risingcampw4.R
import com.example.risingcampw4.databinding.FragmentGrillBinding
import com.example.risingcampw4.databinding.FragmentScoreBinding

class ScoreFragment : BaseFragment<FragmentScoreBinding>(FragmentScoreBinding::inflate) {
    private var score = 0
    private var heart = 3
    lateinit var activityViewModel: ActivityViewModel

    override fun initView() {
        super.initView()

        activityViewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]

        val fragmentViewModel = ViewModelProvider(requireActivity())[FragmentViewModel::class.java]
        fragmentViewModel.message.observe(viewLifecycleOwner, Observer { getFishBread(it) })
    }

    private fun getFishBread(isSuccess: Boolean) {
        if (isSuccess) {
            score += 10
            binding.tvScore.text = "SCORE = $score"
            binding.ivStatus.setImageResource(R.drawable.happy)
        } else {
            when (heart) {
                3 -> binding.ivHeart1.visibility = View.GONE
                2 -> binding.ivHeart2.visibility = View.GONE
                1 -> binding.ivHeart3.visibility = View.GONE
                else -> {
                    activityViewModel.sendMessage(true)
                }
            }
            heart--
            binding.ivStatus.setImageResource(R.drawable.furious)
        }
    }
}