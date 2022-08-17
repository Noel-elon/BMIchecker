package com.example.noelnwaelugo.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.noelnwaelugo.R
import com.example.noelnwaelugo.models.BMIData
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddDetailsFragment : Fragment() {

    private lateinit var weightPicker: NumberPicker
    private lateinit var heightPicker: NumberPicker
    private lateinit var genderPicker: NumberPicker
    private lateinit var nameField: TextInputLayout
    private lateinit var calculateButton: MaterialButton
    private var mInterstitialAd: InterstitialAd? = null

    private val viewModel: BMIViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_details, container, false)
        weightPicker = view.findViewById(R.id.weight_picker)
        heightPicker = view.findViewById(R.id.height_picker)
        genderPicker = view.findViewById(R.id.gender_picker)
        nameField = view.findViewById(R.id.name_et)
        calculateButton = view.findViewById(R.id.calculate_button)

        setUpPickers()
        setOnClickListeners()
        setUpInterstitialAd()


        return view
    }

    private fun setUpPickers() {
        weightPicker.minValue = 0
        weightPicker.maxValue = 100

        heightPicker.minValue = 0
        heightPicker.maxValue = 200

        genderPicker.displayedValues = resources.getStringArray(R.array.Gender)
    }

    private fun validateFields() {
        val name = nameField.editText?.text
        if (name?.isEmpty() == true) {
            Toast.makeText(requireContext(), "Fill the boxes please", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.setName(name.toString())
            processBMI()
            findNavController().navigate(R.id.action_addDetailsFragment_to_resultFragment)
        }
    }

    private fun processBMI() {
        viewModel.calculateBMI(BMIData(weightPicker.value, heightPicker.value))
    }

    private fun setOnClickListeners() {
        calculateButton.setOnClickListener {
            validateFields()
            showAd()
        }
    }

    private fun showAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    private fun setUpInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
            }

            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd = null
            }

            override fun onAdImpression() {
            }

            override fun onAdShowedFullScreenContent() {
            }
        }
    }

}