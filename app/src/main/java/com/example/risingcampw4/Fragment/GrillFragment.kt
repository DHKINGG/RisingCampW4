package com.example.risingcampw4.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.risingcampw4.Model.FishBread
import com.example.risingcampw4.R
import com.example.risingcampw4.databinding.FragmentGrillBinding

class GrillFragment : BaseFragment<FragmentGrillBinding>(FragmentGrillBinding::inflate) {
    private var grills = mutableListOf<FishBread>()
    private var threads = mutableListOf<Thread>()
    private var grillImageViews = mutableListOf<ImageView>()

    override fun initView() {
        super.initView()

        initData()
    }

    private fun initData() {
        initGrills()
        initThreads()
        initGrillsImageView()
        setOnClickListener()
    }

    private fun initGrills() {
        for (i in 0..8) {
            grills.add(FishBread(0))
        }
    }

    private fun initThreads() {
        for (i in 0..8) {
            threads.add(Thread())
        }
    }

    private fun initGrillsImageView() {
        grillImageViews.add(binding.ivGrill1)
        grillImageViews.add(binding.ivGrill2)
        grillImageViews.add(binding.ivGrill3)
        grillImageViews.add(binding.ivGrill4)
        grillImageViews.add(binding.ivGrill5)
        grillImageViews.add(binding.ivGrill6)
        grillImageViews.add(binding.ivGrill7)
        grillImageViews.add(binding.ivGrill8)
        grillImageViews.add(binding.ivGrill9)
    }

    private fun setOnClickListener() {
        for (i in 0..8) {
            grillImageViews[i].setOnClickListener {
                startBake(i)
            }
        }
    }

    // inferred type is Unit but Thread was expected
    private fun startBake(index: Int) {
        grills[index].breadStatus = 1
        changeGrillStatus(index)
        Thread {
            try {
                Log.d("myLog", "thread")
                while(grills[index].breadStatus in 1..3) {
                    Thread.sleep(1000)
                    grills[index].breadStatus++

                    activity?.runOnUiThread {
                        changeGrillStatus(index)
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun changeGrillStatus(index: Int) {
        when(grills[index].breadStatus) {
            0 -> grillImageViews[index].setImageResource(R.drawable.mold)
            1 -> grillImageViews[index].setImageResource(R.drawable.rare)
            2 -> grillImageViews[index].setImageResource(R.drawable.medium)
            3 -> grillImageViews[index].setImageResource(R.drawable.well_done)
            else -> grillImageViews[index].setImageResource(R.drawable.overcooked)
        }
    }
}