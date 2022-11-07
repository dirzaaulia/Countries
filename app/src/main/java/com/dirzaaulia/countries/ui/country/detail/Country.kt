package com.dirzaaulia.countries.ui.country.detail

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.dirzaaulia.countries.R
import com.dirzaaulia.countries.ui.main.MainViewModel
import com.dirzaaulia.countries.utils.*
import com.dirzaaulia.countries.data.model.Country as CountryModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Country(
  viewModel: MainViewModel,
  name: String?,
  iso2: String?
) {

  val countryState by viewModel.countryState.collectAsState()
  val country by viewModel.country.collectAsState()
  val selectedTab by viewModel.selectedTab.collectAsState()

  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  val timezonesMap = CountryModel.parseTimezoneFromJson(country?.timezones)
  val translationsMap = CountryModel.parseTranslationsFromJson(country?.translations)

  LaunchedEffect(viewModel) {
    if (!iso2.isNullOrBlank()) viewModel.getCountryDetailWithISO2(iso2)
  }

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = name.replaceIfNull(),
            style = MaterialTheme.typography.displayMedium
          )
        },
        scrollBehavior = scrollBehavior
      )
    },
    content = { innerPadding ->
      val innerModifier = Modifier.padding(innerPadding)
      Column(modifier = innerModifier) {
        CountryTabMenu(
          tabs = CountryTab.values(),
          tabIndex = selectedTab,
          viewModel = viewModel
        )
        Crossfade(targetState = CountryTab.getTabFromResource(selectedTab)) { destination ->
          when (destination) {
            CountryTab.INFORMATION -> {
              CountryInformationTab(
                country = country,
                timezonesMap = timezonesMap,
                translationsMap = translationsMap,
                isPlaceholder = countryState.isLoading
              )
            }
            CountryTab.STATE -> {
              Box {
                Text(
                  text = "Test",
                  style = MaterialTheme.typography.headlineLarge
                )
              }
            }
          }
        }
      }
    }
  )
}

@Composable
fun CountryTabMenu(
  tabs: Array<CountryTab>,
  tabIndex: Int,
  viewModel: MainViewModel
) {
  TabRow(selectedTabIndex = tabIndex) {
    tabs.forEachIndexed { index, countryTab ->
      Tab(
        selected = tabIndex == index,
        onClick = { viewModel.setSelectedTab(index) },
        text = { Text(text = stringResource(id = countryTab.title)) }
      )
    }
  }
}

enum class CountryTab(@StringRes val title: Int) {
  INFORMATION(R.string.information),
  STATE(R.string.state);

  companion object {
    fun getTabFromResource(index: Int): CountryTab {
      return when (index) {
        0 -> INFORMATION
        1 -> STATE
        else -> INFORMATION
      }
    }
  }
}