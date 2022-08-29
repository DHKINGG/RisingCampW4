package com.example.risingcampw4.Fragment

import android.view.View
import android.widget.ImageView
import com.example.risingcampw4.Model.FishBread
import com.example.risingcampw4.Model.FishBread.Companion.EMPTY_GRILL
import com.example.risingcampw4.Model.FishBread.Companion.MEDIUM
import com.example.risingcampw4.Model.FishBread.Companion.RARE
import com.example.risingcampw4.Model.FishBread.Companion.WELL_DONE
import com.example.risingcampw4.R
import com.example.risingcampw4.databinding.FragmentGrillBinding

class GrillFragment : BaseFragment<FragmentGrillBinding>(FragmentGrillBinding::inflate) {
    private var grills = mutableListOf<FishBread>()
    private var grillImageViews = mutableListOf<ImageView>()
    private var redBeanImageViews = mutableListOf<ImageView>()
    private var redBeanCreamImageViews = mutableListOf<ImageView>()
    private var isRedBeanBottle = false

    override fun initView() {
        super.initView()

        initData()
    }

    private fun initData() {
        initGrills()
        initGrillsImageView()
        initRedBeansImageView()
        initRedBeanCreamsImageView()
        setOnClickListener()
    }

    private fun initGrills() {
        for (i in 0..8) {
            grills.add(FishBread())
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

    private fun initRedBeansImageView() {
        redBeanImageViews.add(binding.ivRedBeanPoint1)
        redBeanImageViews.add(binding.ivRedBeanPoint2)
        redBeanImageViews.add(binding.ivRedBeanPoint3)
        redBeanImageViews.add(binding.ivRedBeanPoint4)
        redBeanImageViews.add(binding.ivRedBeanPoint5)
        redBeanImageViews.add(binding.ivRedBeanPoint6)
        redBeanImageViews.add(binding.ivRedBeanPoint7)
        redBeanImageViews.add(binding.ivRedBeanPoint8)
        redBeanImageViews.add(binding.ivRedBeanPoint9)
    }

    private fun initRedBeanCreamsImageView() {
        redBeanCreamImageViews.add(binding.ivRedBeanCream1)
        redBeanCreamImageViews.add(binding.ivRedBeanCream2)
        redBeanCreamImageViews.add(binding.ivRedBeanCream3)
        redBeanCreamImageViews.add(binding.ivRedBeanCream4)
        redBeanCreamImageViews.add(binding.ivRedBeanCream5)
        redBeanCreamImageViews.add(binding.ivRedBeanCream6)
        redBeanCreamImageViews.add(binding.ivRedBeanCream7)
        redBeanCreamImageViews.add(binding.ivRedBeanCream8)
        redBeanCreamImageViews.add(binding.ivRedBeanCream9)
    }

    private fun setOnClickListener() {
        for (i in 0..8) {
            grillImageViews[i].setOnClickListener {
                if (isRedBeanBottle) {
                    if (!grills[i].isTurnOver && grills[i].breadStatus > 0) {
                        addCream(i)
                    }
                } else {
                    if (grills[i].breadStatus > 0 && !grills[i].isTurnOver) {
                        backStartBake(i)
                    } else {
                        startBake(i)
                    }
                }
            }
        }

        binding.ivRedBeanBottle.setOnClickListener {
            isRedBeanBottle = !isRedBeanBottle
            if (isRedBeanBottle) {
                binding.ivRedBeanBottle.setBackgroundColor(resources.getColor(R.color.yellow))
            } else {
                binding.ivRedBeanBottle.setBackgroundColor(resources.getColor(R.color.white))
            }
        }
    }

    private fun startBake(index: Int) {
        grills[index].breadStatus = 1
        changeGrillStatus(index)
        Thread {
            try {
                while(grills[index].breadStatus in 1..3) {
                    Thread.sleep(5000)
                    if (!grills[index].isTurnOver) {
                        grills[index].breadStatus++
                        activity?.runOnUiThread {
                            changeGrillStatus(index)
                        }
                    }

                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun backStartBake(index: Int) {
        grills[index].backBreadStatus = 1
        grills[index].isTurnOver = true
        redBeanCreamImageViews[index].visibility = View.GONE
        changeBackGrillStatus(index)
        Thread {
            try {
                while(grills[index].backBreadStatus in 1..3) {
                    Thread.sleep(5000)

                    if(grills[index].isTurnOver) {
                        grills[index].backBreadStatus++
                        activity?.runOnUiThread {
                            changeBackGrillStatus(index)
                        }
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun changeGrillStatus(index: Int) {
        if (grills[index].isTurnOver) {
            return
        }
        when(grills[index].breadStatus) {
            EMPTY_GRILL -> grillImageViews[index].setImageResource(R.drawable.mold)
            RARE -> grillImageViews[index].setImageResource(R.drawable.rare)
            MEDIUM -> grillImageViews[index].setImageResource(R.drawable.medium)
            WELL_DONE -> grillImageViews[index].setImageResource(R.drawable.well_done)
            else -> grillImageViews[index].setImageResource(R.drawable.overcooked)
        }
        redBeanImageViews[index].visibility = View.GONE
    }

    private fun changeBackGrillStatus(index: Int) {
        if (!grills[index].isTurnOver) {
            return
        }
        when(grills[index].backBreadStatus) {
            EMPTY_GRILL -> grillImageViews[index].setImageResource(R.drawable.mold)
            RARE -> grillImageViews[index].setImageResource(R.drawable.rare)
            MEDIUM -> grillImageViews[index].setImageResource(R.drawable.medium)
            WELL_DONE -> grillImageViews[index].setImageResource(R.drawable.well_done)
            else -> grillImageViews[index].setImageResource(R.drawable.overcooked)
        }
        if (grills[index].isRedBean) {
            redBeanImageViews[index].visibility = View.VISIBLE
        }
    }

    private fun addCream(index: Int) {
        if (!grills[index].isTurnOver && grills[index].breadStatus > 0) {
            grills[index].isRedBean = true
            redBeanCreamImageViews[index].visibility = View.VISIBLE
            binding.ivRedBeanBottle.setBackgroundColor(resources.getColor(R.color.white))
            isRedBeanBottle = !isRedBeanBottle
        }
    }
}