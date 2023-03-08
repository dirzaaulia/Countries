package com.dirzaaulia.countries.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.dirzaaulia.countries.ui.navigation.NavGraph
import com.dirzaaulia.countries.ui.theme.CountriesTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    WindowCompat.setDecorFitsSystemWindows(window, false)

    val isDynamicColor = DynamicColors.isDynamicColorAvailable()

    super.onCreate(savedInstanceState)

    setContent {
      val navHostController = rememberAnimatedNavController()

      // Update the system bars to be translucent
      val systemUiController = rememberSystemUiController()
      val useDarkIcons = !isSystemInDarkTheme()
      SideEffect {
        systemUiController.setSystemBarsColor(
          color = Color.Transparent,
          darkIcons = useDarkIcons
        )
      }

      CountriesTheme(isDynamicColor = isDynamicColor) {
        Surface {
          NavGraph(navHostController = navHostController)
        }
      }
    }
  }
}