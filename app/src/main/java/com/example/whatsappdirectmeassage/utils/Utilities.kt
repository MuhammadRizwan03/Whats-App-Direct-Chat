package com.example.whatsappdirectmeassage.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.net.toUri
import com.example.whatsappdirectmeassage.R
import com.example.whatsappdirectmeassage.activities.MyApplication
import com.example.whatsappdirectmeassage.models.Country
import java.util.Locale
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val countries: List<Country> = loadCountriesFromAssets(MyApplication.getAppContext())

private fun getDefaultCountry(): Country? {
    val deviceCountryNameCode = Locale.getDefault().country
    for (country in countries) {
        Log.e("jhsdkfhsd", "getDefaultCountry: $deviceCountryNameCode", )
        Log.e("jhsdkfhsd", "getDefaultCountry: ${country.phone_code}", )
        if (country.name_code.lowercase() == deviceCountryNameCode.lowercase()) return country
    }
    return null
}

val deviceDefaultCountry = getDefaultCountry()

fun Context.createToast(@StringRes messageResource: Int) {
    Toast.makeText(this, messageResource, Toast.LENGTH_SHORT).show()
}

/*//detect a country using the ISD code in the beginning.
fun detectCountry(phoneNumber: String): Country? {
    countries.forEach {
        if (phoneNumber.replace("+", "").startsWith(it.isdCode)) return it
    }
    return null
}*/

fun getLaunchIntent(phoneNumber: String, message: String, business: Boolean): Intent {

    val total = "https://api.whatsapp.com/send?phone=" +
            phoneNumber.replace("+", "") + "&text=${message}"

    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = total.toUri()
        `package` = if (business) "com.whatsapp.w4b" else "com.whatsapp"
    }
    return intent
}

fun Intent.launchIfResolved(context: Context) {
    if (resolveActivity(context.packageManager) == null) context.createToast(R.string.not_installed)
    else context.startActivity(this)
}

fun loadCountriesFromAssets(context: Context, fileName: String = "countries.json"): List<Country> {
    return try {
        val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val countryListType = object : TypeToken<List<Country>>() {}.type
        Gson().fromJson(json, countryListType)
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}