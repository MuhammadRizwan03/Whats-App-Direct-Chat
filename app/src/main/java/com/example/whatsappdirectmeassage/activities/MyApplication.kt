package com.example.whatsappdirectmeassage.activities

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder

class MyApplication : Application() {

    lateinit var imageLoader : ImageLoader

    override fun onCreate() {
        super.onCreate()
        instance = this

        imageLoader = ImageLoader.Builder(this)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }

    companion object {
        private lateinit var instance: MyApplication

        fun getAppContext(): Context = instance.applicationContext

        fun getImageLoader() = this@Companion.instance.imageLoader
    }
}
