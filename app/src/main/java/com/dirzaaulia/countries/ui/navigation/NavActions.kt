package com.dirzaaulia.countries.ui.navigation

import androidx.navigation.NavHostController

class NavActions(navHostController: NavHostController) {

  val navigateToCountry: (String, String) -> Unit = { name, iso2 ->
    NavScreen.Country.apply {
      navHostController.navigate(
        routeWithArgument
          .replace("{$argument0}", name)
          .replace("{$argument1}", iso2)
      )
    }
  }
}