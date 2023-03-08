package com.dirzaaulia.countries

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class  App: Application(), ImageLoaderFactory {

  override fun onCreate() {
    super.onCreate()
    DynamicColors.applyToActivitiesIfAvailable(this)
    Timber.plant(Timber.DebugTree())
  }

  override fun newImageLoader(): ImageLoader {
    return ImageLoader.Builder(this).build()
  }

}