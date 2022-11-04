package com.dirzaaulia.countries.ui.navigation

sealed class NavScreen(val route: String) {
  object CountryList : NavScreen("CountryList")

  object Country: NavScreen("Country") {
    const val routeWithArgument: String = "Country/{index}"
    const val argument0: String = "index"
  }
}