package com.example.noelnwaelugo.datasource

import com.example.noelnwaelugo.models.BMIData
import com.example.noelnwaelugo.utils.MAX_BMI
import com.example.noelnwaelugo.utils.MIN_BMI
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
        return if (bmi < MAX_BMI && bmi > MIN_BMI) {
            "Hello $name, you are normal"
        } else if (bmi < MIN_BMI) {
            "Hello $name, you are underweight"
        } else {
            "Hello $name, you are overweight"
        }
    }
}