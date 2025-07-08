package com.example.whatsappdirectmeassage.utils

import android.widget.ImageView
import coil.request.ImageRequest
import com.example.whatsappdirectmeassage.activities.MyApplication

fun ImageView.loadSvgImage(url: String) {

    val request = ImageRequest.Builder(MyApplication.getAppContext())
        .data(url)
        .crossfade(true)
        .target(this)
        .build()
    MyApplication.getImageLoader().enqueue(request)

}
