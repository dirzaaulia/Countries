package com.dirzaaulia.countries.ui.navigation

import androidx.navigation.NavHostController

class NavActions(navHostController: NavHostController) {

  val navigateToCountry: (Int) -> Unit = { index: Int ->
    NavScreen.Country.apply {
      navHostController.navigate(
        routeWithArgument.replace("{$argument0}", index.toString())
      )
    }
  }
}