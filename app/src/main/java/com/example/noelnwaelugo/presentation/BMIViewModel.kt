package com.example.noelnwaelugo.presentation

import androidx.lifecycle.ViewModel
import com.example.noelnwaelugo.datasource.BMIRepository
import com.example.noelnwaelugo.models.BMIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BMIViewModel @Inject constructor(
    private val repository: BMIRepository
) : ViewModel() {

    private val _bmi = MutableStateFlow(0.0)
    val bmi = _bmi as StateFlow<Double>

    private val _remark = MutableStateFlow("")
    val remark = _remark as StateFlow<String>

    private val _name = MutableStateFlow("")
    val name = _name as StateFlow<String>

    fun calculateBMI(bmiData: BMIData) {
        _bmi.value = repository.calculateBMI(bmiData)
    }

    fun getRemark() {
        _remark.value = repository.getUserRemark(name.value, bmi.value)
    }

    fun setName(name: String) {
        _name.value = name
    }

}