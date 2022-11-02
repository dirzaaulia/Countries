package com.dirzaaulia.allaboutvalorant.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dirzaaulia.allaboutvalorant.data.model.Agent
import com.dirzaaulia.allaboutvalorant.repository.Repository
import com.dirzaaulia.allaboutvalorant.utils.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val repository: Repository
): ViewModel() {

  private val _agents: MutableStateFlow<List<Agent>?> =
    MutableStateFlow(null)
  val agents = _agents.asStateFlow()

  init {
    getAgents()
  }

  private fun getAgents() = repository.getAgents()
    .onEach {
      it.success { response ->
        _agents.value = response.data
      }
    }
    .launchIn(viewModelScope)
}