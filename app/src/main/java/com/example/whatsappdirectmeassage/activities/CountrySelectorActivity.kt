package com.example.whatsappdirectmeassage.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsappdirectmeassage.adapters.CountriesAdapter
import com.example.whatsappdirectmeassage.databinding.ActivityCountrySelectBinding
import com.example.whatsappdirectmeassage.utils.deviceDefaultCountry
import com.example.whatsappdirectmeassage.utils.loadSvgImage

class CountrySelectorActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCountrySelectBinding.inflate(layoutInflater) }

    private val adapter = CountriesAdapter { countrySelected(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.counter.text = countries.size.toString()
        binding.recycler.adapter = adapter

        binding.scroller.attachRecyclerView(binding.recycler)
        binding.scroller.setSectionIndexer(adapter)

        setupDeviceCountry()
    }

    //when a country is selected by the user, its position is sent back to the main activity.
    private fun countrySelected(position: Int) {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra("country", position) })
        finish()
    }

    private fun setupDeviceCountry() {

        val deviceCountry = deviceDefaultCountry
        if (deviceCountry == null) {
            binding.deviceDefault.root.visibility = View.GONE
            binding.defaultHeader.visibility = View.GONE
            return
        }

        val name = "${deviceCountry.name}, ${deviceCountry.name_code}"
        val dialingCode = "+${deviceCountry.phone_code}"

        binding.deviceDefault.countryName.text = name
        binding.deviceDefault.countryCode.text = dialingCode
        binding.deviceDefault.flag.loadSvgImage(deviceCountry.flag_url)
//
//        val request = ImageRequest.Builder(this)
//            .data(deviceCountry.flag_url)
//            .crossfade(true)
//            .target(binding.deviceDefault.flag)
//            .build()
//        MyApplication.getImageLoader().enqueue(request)


        binding.deviceDefault.root.setOnClickListener { countrySelected(position = -1) }
    }

}