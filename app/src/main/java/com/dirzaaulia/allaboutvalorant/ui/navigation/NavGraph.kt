package com.dirzaaulia.allaboutvalorant.ui.navigation

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
import com.dirzaaulia.allaboutvalorant.ui.agent.AgentDetail
import com.dirzaaulia.allaboutvalorant.ui.agent.AgentList
import com.dirzaaulia.allaboutvalorant.ui.main.MainViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
fun NavGraph(
  navHostController: NavHostController,
  viewModel: MainViewModel = hiltViewModel()
) {

  val actions = remember(navHostController) { NavActions(navHostController) }
  val agents by viewModel.agents.collectAsState()
  
  AnimatedNavHost(
    navController = navHostController,
    startDestination = NavScreen.Home.route
  ) {
    composable(NavScreen.Home.route) {
      AgentList(
        agents = agents,
        navigateToAgent = actions.navigateToAgent
      )
    }
    composable(
      route = NavScreen.Agent.routeWithArgument,
      arguments = listOf(navArgument(NavScreen.Agent.argument0) {
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
          val index = argument.getInt(NavScreen.Agent.argument0)
          val agent = agents?.get(index)

          agent?.let {
            AgentDetail(agent = it)
          }
        }
      }
    }
  }
}