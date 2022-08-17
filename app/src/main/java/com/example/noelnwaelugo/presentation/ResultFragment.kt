package com.example.noelnwaelugo.presentation


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.noelnwaelugo.R
import com.example.noelnwaelugo.utils.NATIVE_AD_ID
import com.example.noelnwaelugo.utils.screenShot
import com.example.noelnwaelugo.utils.share
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ResultFragment : Fragment() {

    lateinit var adTemplate: TemplateView
    private lateinit var bmiResultTv: TextView
    private lateinit var bmiRemarkTv: TextView
    private lateinit var shareButton: MaterialButton
    private lateinit var rateButton: MaterialButton
    private lateinit var resultCard: MaterialCardView
    private val viewModel: BMIViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        adTemplate = view.findViewById(R.id.bmi_ad_template)
        bmiResultTv = view.findViewById(R.id.bmi_result_tv)
        bmiRemarkTv = view.findViewById(R.id.user_remark_tv)
        shareButton = view.findViewById(R.id.share_button)
        rateButton = view.findViewById(R.id.rate_button)
        resultCard = view.findViewById(R.id.result_card)

        setupViews()
        observeFlow()
        setClickListeners()
        setUpAd()

        return view
    }

    private fun setUpAd() {
        MobileAds.initialize(requireContext())
        val adLoader = AdLoader.Builder(requireContext(), NATIVE_AD_ID)
            .forNativeAd {
                val styles =
                    NativeTemplateStyle.Builder().build()
                adTemplate.setStyles(styles)
                adTemplate.setNativeAd(it)
            }
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun setupViews() {
        bmiResultTv.text = viewModel.bmi.value.toString()
        viewModel.getRemark()
    }

    private fun observeFlow() {
        lifecycleScope.launch {
            viewModel.remark.collect {
                bmiRemarkTv.text = it
            }
        }
    }

    private fun setClickListeners() {
        shareButton.setOnClickListener {
            val screenShot = screenShot(resultCard)
            screenShot?.let { shot ->
                share(shot, requireContext())
            }
        }

        rateButton.setOnClickListener {
            launchStore()
        }
    }

    private fun launchStore() {
        val packageName = requireActivity().packageName
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }


}