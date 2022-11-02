package com.dirzaaulia.allaboutvalorant.ui.navigation

import androidx.navigation.NavHostController

class NavActions(navHostController: NavHostController) {

  val navigateToHome: () -> Unit = {
    NavScreen.Home.apply {
      navHostController.navigate(this.route)
    }
  }

  val navigateToAgent: (Int) -> Unit = { index: Int ->
    NavScreen.Agent.apply {
      navHostController.navigate(
        routeWithArgument.replace("{$argument0}", index.toString())
      )
    }
  }
}