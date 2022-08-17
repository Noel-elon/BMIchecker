package com.example.noelnwaelugo.datasource

import com.example.noelnwaelugo.models.BMIData
import javax.inject.Inject
import kotlin.math.roundToInt

class BMIRepoImpl @Inject constructor() : BMIRepository {
    override fun calculateBMI(bmiData: BMIData): Double {
        val heightInMeters = bmiData.height.toDouble().div(100)
        val squaredHeight = heightInMeters.times(heightInMeters)
        val bmi = bmiData.weight.div(squaredHeight)
        return (bmi * 100.0).roundToInt() / 100.0
    }

    override fun getUserRemark(name: String, bmi: Double): String {
        return if (bmi < 25 && bmi > 18.5) {
            "Hello $name, you are normal"
        } else if (bmi < 18.5) {
            "Hello $name, you are underweight"
        } else {
            "Hello $name, you are overweight"
        }
    }
}