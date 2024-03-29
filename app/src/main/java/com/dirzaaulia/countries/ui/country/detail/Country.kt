package com.dirzaaulia.countries.ui.country.detail

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dirzaaulia.countries.R
import com.dirzaaulia.countries.ui.common.NetworkImage
import com.dirzaaulia.countries.ui.main.MainViewModel
import com.dirzaaulia.countries.utils.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlin.random.Random
import com.dirzaaulia.countries.data.model.Country as CountryModel

@Composable
fun Country(
    viewModel: MainViewModel,
    name: String?,
    iso2: String?
) {

    val countryState by viewModel.countryState.collectAsState()
    val country by viewModel.country.collectAsState()
    val statesState by viewModel.statesState.collectAsState()
    val states by viewModel.states.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val timezonesMap = CountryModel.parseTimezoneFromJson(country?.timezones)
    val translationsMap = CountryModel.parseTranslationsFromJson(country?.translations)

    LaunchedEffect(viewModel) {
        if (!iso2.isNullOrBlank()) {
            viewModel.apply {
                getCountryDetailWithISO2(iso2)
                getStatesFromCountry(iso2)
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = name.replaceIfNull()
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
                Crossfade(
                    targetState = CountryTab.getTabFromResource(selectedTab),
                    label = stringResource(R.string.country_tab)
                ) { destination ->
                    when (destination) {
                        CountryTab.INFORMATION -> {
                            CountryInformationTab(
                                country = country,
                                timezonesMap = timezonesMap,
                                translationsMap = translationsMap,
                                responseResult = countryState,
                                retry = { iso2?.let { viewModel.getCountryDetailWithISO2(it) } }
                            )
                        }

                        CountryTab.STATE -> {
                            CountryStateTab(
                                stateList = states,
                                responseResult = statesState,
                                retry = { iso2?.let { viewModel.getStatesFromCountry(it) } }
                            )
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

@Composable
fun CountryDetailItem(data: Pair<String, String>) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.first,
                style = MaterialTheme.typography.headlineMedium
            )

            if (data.first == "Flag") {
                Card {
                    NetworkImage(url = data.second)
                }
            } else {
                val formattedValue = if (data.second.contains("U+")) {
                    data.second.unicodeEmojiToHtmlEmoji().toString()
                } else {
                    data.second
                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formattedValue,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun CountryDetailItem(value: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = value,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun CountryDetailItemPlaceholder() {
    val randomHeight = Random.nextInt(56, 144)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(randomHeight.dp)
            .placeholder(
                visible = true,
                color = MaterialTheme.colorScheme.surfaceVariant,
                highlight = PlaceholderHighlight.shimmer()
            )
    ) {}
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