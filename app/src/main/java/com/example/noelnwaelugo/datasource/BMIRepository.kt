package com.example.noelnwaelugo.datasource

import com.example.noelnwaelugo.models.BMIData

interface BMIRepository {
    fun calculateBMI(bmiData: BMIData): Double
    fun getUserRemark(name: String, bmi: Double): String
}