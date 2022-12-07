package com.dirzaaulia.countries.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dirzaaulia.countries.data.model.Country
import com.dirzaaulia.countries.data.model.State
import com.dirzaaulia.countries.repository.Repository
import com.dirzaaulia.countries.utils.ResponseResult
import com.dirzaaulia.countries.utils.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val repository: Repository
): ViewModel() {

  private val _filter = MutableStateFlow("")
  val filter = _filter.asStateFlow()

  private val _countriesState: MutableStateFlow<ResponseResult<List<Country>?>> =
    MutableStateFlow(ResponseResult.Success(null))
  val countriesState = _countriesState.asStateFlow()

  private val _countries: MutableStateFlow<List<Country>> = MutableStateFlow(emptyList())
  val countries = _countries.asStateFlow()

  private val _countryState: MutableStateFlow<ResponseResult<Country?>> =
    MutableStateFlow(ResponseResult.Success(null))
  val countryState = _countryState.asStateFlow()

  private val _country: MutableStateFlow<Country?> = MutableStateFlow(null)
  val country = _country.asStateFlow()

  private val _statesState: MutableStateFlow<ResponseResult<List<State>>> =
    MutableStateFlow(ResponseResult.Success(emptyList()))
  val statesState = _statesState.asStateFlow()


  private val _states: MutableStateFlow<List<State>> =
    MutableStateFlow(emptyList())
  val states = _states.asStateFlow()

  private val _selectedTab: MutableStateFlow<Int> = MutableStateFlow(0)
  val selectedTab = _selectedTab.asStateFlow()

  init {
    getCountries()
  }

  fun setSelectedTab(tabIndex: Int) {
    _selectedTab.value = tabIndex
  }

  fun setFilter(filter: String) {
    _filter.value = filter
  }

  private fun getCountries() {
    repository.getCountries()
      .onEach {
        _countriesState.value = it
        it.success { response ->
          _countries.value = response
        }
      }
      .launchIn(viewModelScope)
  }

  fun getCountryDetailWithISO2(iso2: String) {
    repository.getCountryDetailWithISO2(iso2)
      .onEach {
        _countryState.value = it
        it.success { country -> _country.value = country }
      }
      .launchIn(viewModelScope)
  }

  fun getStatesFromCountry(iso2: String) {
    repository.getStatesFromCountry(iso2)
      .onEach {
        _statesState.value = it
        it.success { list ->
          _states.value = list
        }
      }
      .launchIn(viewModelScope)
  }

}