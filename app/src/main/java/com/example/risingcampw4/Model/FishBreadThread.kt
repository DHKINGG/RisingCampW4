package com.example.risingcampw4.Model

import android.widget.ImageView

class FishBreadThread: Thread() {
    var breadStatus = 0

    override fun run() {
        super.run()

        try {
            while(true) {
                Thread.sleep(5000)

            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}