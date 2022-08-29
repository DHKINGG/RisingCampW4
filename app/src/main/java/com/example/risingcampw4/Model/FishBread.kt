package com.example.risingcampw4.Model

class FishBread(var breadStatus: Int = 0, var backBreadStatus: Int = 0, var isRedBean: Boolean = false, var isTurnOver: Boolean = false) {
    companion object {
        const val EMPTY_GRILL = 0
        const val RARE = 1
        const val MEDIUM = 2
        const val WELL_DONE = 3
        const val OVER_COOK = 4
    }
}