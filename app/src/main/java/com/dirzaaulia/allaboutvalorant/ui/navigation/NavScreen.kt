package com.dirzaaulia.allaboutvalorant.ui.navigation

sealed class NavScreen(val route: String) {
  object Home : NavScreen("Home")

  object Agent: NavScreen("Agent") {
    const val routeWithArgument: String = "Agent/{index}"
    const val argument0: String = "index"
  }
}