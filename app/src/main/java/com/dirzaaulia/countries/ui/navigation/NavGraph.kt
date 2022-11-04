package com.dirzaaulia.countries.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.dirzaaulia.countries.ui.country.Country
import com.dirzaaulia.countries.ui.country.CountryList
import com.dirzaaulia.countries.ui.main.MainViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
fun NavGraph(
  navHostController: NavHostController,
  viewModel: MainViewModel = hiltViewModel()
) {

  val actions = remember(navHostController) { NavActions(navHostController) }
  val countries by viewModel.countries.collectAsState()

  AnimatedNavHost(
    navController = navHostController,
    startDestination = NavScreen.CountryList.route
  ) {
    composable(NavScreen.CountryList.route) {
      CountryList(
        countries = countries,
        navigateToCountryDetail = actions.navigateToCountry
      )
    }
    composable(
      route = NavScreen.Country.routeWithArgument,
      arguments = listOf(navArgument(NavScreen.Country.argument0) {
        type = NavType.IntType
      }),
      enterTransition = {
        fadeIn(animationSpec = tween(700))
      },
      exitTransition = {
        fadeOut(animationSpec = tween(700))
      }
    ) { backStackEntry ->
      backStackEntry.arguments.let { bundle ->
        bundle?.let { argument ->
          val index = argument.getInt(NavScreen.Country.argument0)
          val country = countries[index]

          if (countries.isNotEmpty())
            Country(
              viewModel = viewModel,
              iso2 = country.iso2
            )
        }
      }
    }
  }
}