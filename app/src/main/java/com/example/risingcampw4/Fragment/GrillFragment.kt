package com.example.risingcampw4.Fragment

import android.util.Log
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
    private var grills = mutableListOf<FishBread>() // 붕어빵 상태배열
    private var grillImageViews = mutableListOf<ImageView>()    // 붕어빵 이미지뷰 배열
    private var redBeanImageViews = mutableListOf<ImageView>()  // 붕어빵 눈 이미지뷰 배열
    private var redBeanCreamImageViews = mutableListOf<ImageView>() // 붕어빵 위의 크림 이미뷰 배열
    private var isRedBeanBottle = false // 팥 병 선택 여부
    private var score = 0   // 점수
    private var heart = 5   // 목숨 개수

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
                if (isRedBeanBottle) {  // 팥 병 클릭됐을때
                    if (!grills[i].isTurnOver && grills[i].breadStatus > EMPTY_GRILL) {
                        addCream(i)
                    }
                } else if (grills[i].backBreadStatus > EMPTY_GRILL && grills[i].isTurnOver) { // 뒷면일때 클릭
                    takeOutBread(i)
                } else {
                    if (grills[i].breadStatus > EMPTY_GRILL && !grills[i].isTurnOver) { // 팥 병 활성화 안되고 앞면일때 클릭
                        backStartBake(i)
                    } else {    // 빈 그릴일때 클릭
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
                    } else {
                        break
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun backStartBake(index: Int) {
        grills[index].backBreadStatus = RARE
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
                    } else {
                        break
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
        if (!grills[index].isTurnOver && grills[index].breadStatus > EMPTY_GRILL) {
            grills[index].isRedBean = true
            redBeanCreamImageViews[index].visibility = View.VISIBLE
            binding.ivRedBeanBottle.setBackgroundColor(resources.getColor(R.color.white))
            isRedBeanBottle = !isRedBeanBottle
        }
    }

    private fun takeOutBread(index: Int) {
        if (grills[index].breadStatus == WELL_DONE && grills[index].backBreadStatus == WELL_DONE && grills[index].isRedBean) {
            score += 10
        } else {
            heart -= 1
        }

        clearBread(index)
        grillImageViews[index].setImageResource(R.drawable.mold)
        redBeanCreamImageViews[index].visibility = View.GONE
        redBeanImageViews[index].visibility = View.GONE

        Log.d("myLog", "score = ${score}, heart = ${heart}")
    }

    private fun clearBread(index: Int) {
        grills[index].breadStatus = 0
        grills[index].backBreadStatus = 0
        grills[index].isRedBean = false
        grills[index].isTurnOver = false
    }
}