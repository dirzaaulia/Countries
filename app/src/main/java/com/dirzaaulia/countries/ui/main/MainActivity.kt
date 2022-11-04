package com.dirzaaulia.countries.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.dirzaaulia.countries.ui.navigation.NavGraph
import com.dirzaaulia.countries.ui.theme.AllAboutValorantTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private lateinit var navHostController: NavHostController

  override fun onCreate(savedInstanceState: Bundle?) {
    WindowCompat.setDecorFitsSystemWindows(window, false)

    super.onCreate(savedInstanceState)

    setContent {
      navHostController = rememberAnimatedNavController()

      AllAboutValorantTheme {
        Surface {
          NavGraph(navHostController = navHostController)
        }
      }
    }
  }
}