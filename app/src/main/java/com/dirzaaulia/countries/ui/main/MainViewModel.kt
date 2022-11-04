package com.dirzaaulia.countries.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dirzaaulia.countries.data.model.Country
import com.dirzaaulia.countries.repository.Repository
import com.dirzaaulia.countries.utils.ResponseResult
import com.dirzaaulia.countries.utils.success
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

  private val _countries: MutableStateFlow<List<Country>> = MutableStateFlow(emptyList())
  val countries = _countries.asStateFlow()

  private val _country: MutableStateFlow<Country?> = MutableStateFlow(null)
  val country = _country.asStateFlow()

  init {
    getCountries()
  }

  private fun getCountries() {
    repository.getCountries()
      .onEach {
        it.success { countries ->
          _countries.value = countries
        }
      }
      .launchIn(viewModelScope)
  }

  fun getCountryDetailWithISO2(iso2: String) {
    repository.getCountryDetailWithISO2(iso2)
      .onEach {
        it.success { country ->
          _country.value = country
        }
      }
      .launchIn(viewModelScope)
  }

}