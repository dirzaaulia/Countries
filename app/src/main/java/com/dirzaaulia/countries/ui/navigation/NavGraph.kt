package com.dirzaaulia.countries.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.dirzaaulia.countries.ui.country.detail.Country
import com.dirzaaulia.countries.ui.country.list.CountryList
import com.dirzaaulia.countries.ui.main.MainViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
fun NavGraph(
  navHostController: NavHostController,
  viewModel: MainViewModel = hiltViewModel()
) {

  val actions = remember(navHostController) { NavActions(navHostController) }

  AnimatedNavHost(
    navController = navHostController,
    startDestination = NavScreen.CountryList.route
  ) {
    composable(NavScreen.CountryList.route) {
      CountryList(
        viewModel = viewModel,
        navigateToCountryDetail = actions.navigateToCountry
      )
    }
    composable(
      route = NavScreen.Country.routeWithArgument,
      arguments = listOf(
        navArgument(NavScreen.Country.argument0) {
          type = NavType.StringType
        },
        navArgument(NavScreen.Country.argument1) {
          type = NavType.StringType
        }
      ),
      enterTransition = {
        fadeIn(animationSpec = tween(700))
      },
      exitTransition = {
        fadeOut(animationSpec = tween(700))
      }
    ) { backStackEntry ->
      backStackEntry.arguments.let { bundle ->
        bundle?.let { argument ->
          val name = argument.getString(NavScreen.Country.argument0)
          val iso2 = argument.getString(NavScreen.Country.argument1)

          Country(
            viewModel = viewModel,
            name = name,
            iso2 = iso2
          )
        }
      }
    }
  }
}