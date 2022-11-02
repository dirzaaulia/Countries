package com.dirzaaulia.allaboutvalorant.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dirzaaulia.allaboutvalorant.ui.navigation.NavGraph
import com.dirzaaulia.allaboutvalorant.ui.theme.AllAboutValorantTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private lateinit var navHostController: NavHostController

  override fun onCreate(savedInstanceState: Bundle?) {
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