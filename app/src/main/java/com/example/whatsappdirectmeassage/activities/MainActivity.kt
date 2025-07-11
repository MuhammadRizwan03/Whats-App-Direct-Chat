package com.example.whatsappdirectmeassage.activities

import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.request.ImageRequest
import com.example.whatsappdirectmeassage.R
import com.example.whatsappdirectmeassage.databinding.ActivityMainBinding
import com.example.whatsappdirectmeassage.models.Country
import com.example.whatsappdirectmeassage.utils.countries
import com.example.whatsappdirectmeassage.utils.createToast
import com.example.whatsappdirectmeassage.utils.deviceDefaultCountry
import com.example.whatsappdirectmeassage.utils.getLaunchIntent
import com.example.whatsappdirectmeassage.utils.launchIfResolved

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode != RESULT_OK || it.data == null) return@registerForActivityResult
            val countryIndex = it.data?.getIntExtra("country", -1)
            if (countryIndex == null || countryIndex == -1) countrySelected(deviceDefaultCountry)
            else countrySelected(countries[countryIndex])
        }
    private var selectedCountry: Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (selectedCountry != null) return
        var defaultCountry = deviceDefaultCountry
        if (savedInstanceState != null) {
            val position = savedInstanceState.getInt("country", -1)
            if (position != -1) defaultCountry = countries[position]

            val enteredNumber = savedInstanceState.getString("number")
            val enteredText = savedInstanceState.getString("message")

            binding.waNumber.setText(enteredNumber)
            binding.optionalMessage.setText(enteredText)
        }
        countrySelected(defaultCountry)
        setClickListeners()


    }

    override fun onResume() {
        super.onResume()

        val clipboardManager = getSystemService(ClipboardManager::class.java)
        if (!clipboardManager?.hasPrimaryClip()!!) {
            binding.paste.visibility = View.GONE
            return
        }

        val clipItem = clipboardManager.primaryClip?.getItemAt(0)
        if (clipItem?.text == null) {
            binding.paste.visibility = View.GONE
            return
        }

        val copiedText = clipItem.text.toString()
        if (!PhoneNumberUtils.isGlobalPhoneNumber(copiedText)) {
            binding.paste.visibility = View.GONE
            return
        }
        binding.paste.visibility = View.VISIBLE
        binding.paste.setOnClickListener { binding.waNumber.setText(copiedText) }

    }

    override fun onDestroy() {
        super.onDestroy()
        activityLauncher.unregister()
    }

    private fun countrySelected(selectedCountry: Country?) {
        if (selectedCountry == null) return
        val request = ImageRequest.Builder(this)
            .data(selectedCountry.flag_url)
            .crossfade(true)
            .target(binding.flag)
            .build()
        MyApplication.getImageLoader().enqueue(request)
        binding.isdCode.text = selectedCountry.phone_code
        this.selectedCountry = selectedCountry
    }

    private fun setClickListeners() {

        binding.selectCountry.setOnClickListener {
            activityLauncher.launch(Intent(this, CountrySelectorActivity::class.java))
        }

        binding.sendBusiness.setOnClickListener { sendClicked(business = true) }
        binding.send.setOnClickListener { sendClicked(business = false) }
    }

    private fun sendClicked(business: Boolean) {
        val rawInput = binding.waNumber.text?.toString() ?: ""

        if (rawInput.isBlank() || rawInput == "+") {
            createToast(R.string.enter_number_warn)
            return
        }

        val cleanedNumber = rawInput.replace(Regex("\\D"), "")
        if (cleanedNumber.length < 7) {
            createToast(R.string.invalid_number_warn)
            return
        }

        val message = binding.optionalMessage.text?.toString().orEmpty()
        val phoneNumberWithIsd = selectedCountry?.phone_code + cleanedNumber
        Log.d("sendClicked", "Formatted Number: $phoneNumberWithIsd")
        getLaunchIntent(phoneNumberWithIsd, message, business).launchIfResolved(this)
    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("country", countries.indexOf(selectedCountry))
        outState.putString("number", binding.waNumber.text.toString())
        outState.putString("message", binding.optionalMessage.text.toString())
    }*/


}