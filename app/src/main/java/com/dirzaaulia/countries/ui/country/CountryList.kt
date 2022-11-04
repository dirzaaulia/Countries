package com.dirzaaulia.countries.ui.country

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.dirzaaulia.countries.data.model.Country
import com.dirzaaulia.countries.utils.NetworkImage
import com.dirzaaulia.countries.utils.replaceIfNull

@Composable
fun CountryList(
  countries: List<Country>,
  navigateToCountryDetail: (Int) -> Unit
) {
  if (countries.isNotEmpty()) {
    LazyColumn {
      itemsIndexed(countries) { index, country ->
        CountryItem(
          index = index,
          country = country,
          navigateToCountryDetail = navigateToCountryDetail
        )
      }
    }
  }
}

@Composable
fun CountryItem(
  index: Int,
  country: Country,
  navigateToCountryDetail: (Int) -> Unit
) {
  Card(
    modifier = Modifier
      .padding(8.dp)
      .clickable { navigateToCountryDetail(index) },
    shape = MaterialTheme.shapes.small
  ) {
    Box(
      contentAlignment = Alignment.BottomStart
    ) {
      NetworkImage(
        modifier = Modifier
          .fillMaxWidth()
          .height(244.dp),
        url = "https://countryflagsapi.com/png/${country.iso2}",
        contentScale = ContentScale.FillBounds
      )
      Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface.copy(alpha = 0.5f)
      ) {
        Text(
          modifier = Modifier.padding(horizontal = 16.dp),
          text = country.name.replaceIfNull(),
          color = MaterialTheme.colors.onSurface,
          style = MaterialTheme.typography.h4
        )
      }
    }
  }
}