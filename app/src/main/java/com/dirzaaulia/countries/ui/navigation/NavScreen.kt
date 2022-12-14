package com.dirzaaulia.countries.ui.navigation

sealed class NavScreen(val route: String) {
  object CountryList : NavScreen("CountryList")

  object Country: NavScreen("Country") {
    const val routeWithArgument: String = "Country/{name}/{iso2}"
    const val argument0: String = "name"
    const val argument1: String = "iso2"
  }
}