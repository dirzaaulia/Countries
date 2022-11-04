package com.dirzaaulia.countries.ui.country

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dirzaaulia.countries.ui.common.CollapsingToolbar
import com.dirzaaulia.countries.ui.common.StaggeredVerticalGrid
import com.dirzaaulia.countries.ui.main.MainViewModel
import com.dirzaaulia.countries.utils.replaceIfNull
import com.dirzaaulia.countries.utils.unicodeEmojiToHtmlEmoji
import kotlin.collections.Map.Entry
import com.dirzaaulia.countries.data.model.Country as CountryModel

@Composable
fun Country(
  viewModel: MainViewModel,
  iso2: String?
) {

  val country by viewModel.country.collectAsState()
  val timezonesMap = CountryModel.parseTimezoneFromJson(country?.timezones)
  val translationsMap = CountryModel.parseTranslationsFromJson(country?.translations)

  LaunchedEffect(viewModel) {
    if (!iso2.isNullOrBlank()) {
      viewModel.getCountryDetailWithISO2(iso2)
    }
  }

  country?.let {
    CollapsingToolbar(
      title = it.name.replaceIfNull(),
      imageUrl = "https://countryflagsapi.com/png/$iso2"
    ) {
      StaggeredVerticalGrid(
        maxColumnWidth = 220.dp,
        modifier = Modifier.padding(4.dp)
      ) {
        CountryModel.setDataMap(it).map { map ->
         CountryDetailItem(map = map)
        }
        timezonesMap?.map { map ->
          CountryDetailItem(map = map)
        }
        translationsMap?.map { map ->
          CountryDetailItem(map = map)
        }
      }
    }
  }
}

@Composable
fun CountryDetailItem(map: Entry<String, String>) {
  Card(
    modifier = Modifier
      .padding(8.dp)
      .fillMaxWidth(),
    shape = MaterialTheme.shapes.small
  ) {
    Column (
      modifier = Modifier.padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = map.key,
        style = MaterialTheme.typography.h5
      )
      val formattedValue = if (map.value.contains("U+")) {
        map.value.unicodeEmojiToHtmlEmoji().toString()
      } else {
        map.value
      }

      Text(
        text = formattedValue,
        style = MaterialTheme.typography.h6
      )
    }
  }
}