package com.dirzaaulia.countries.ui.country.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.dirzaaulia.countries.data.model.Country
import com.dirzaaulia.countries.ui.common.CommonError
import com.dirzaaulia.countries.ui.main.MainViewModel
import com.dirzaaulia.countries.utils.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun CountryList(
  viewModel: MainViewModel,
  navigateToCountryDetail: (String, String) -> Unit,
) {

  val countriesState by viewModel.countriesState.collectAsState()
  val filter by viewModel.filter.collectAsState()

  Column(
    modifier = Modifier.statusBarsPadding()
  ) {
    CountryFilter(
      viewModel = viewModel,
      isEnabled = countriesState.isSucceeded,
    )
    LazyColumn(
      modifier = Modifier.padding(top = 16.dp)
    ) {
      when {
        countriesState.isLoading -> {
          items(10) {
            CountryItemPlaceholder()
          }
        }
        countriesState.isSucceeded -> {
          countriesState.success { data ->
            val filteredCountry = if (filter.isBlank()) {
              data
            } else {
              data?.filter {
                it.name?.contains(filter, true) == true
              }
            }

            if (filteredCountry?.isNotEmpty() == true) {
              items(filteredCountry) { country ->
                CountryItem(
                  name = country.name.replaceIfNull(),
                  iso2 = country.iso2.replaceIfNull(),
                  country = country,
                  navigateToCountryDetail = navigateToCountryDetail
                )
              }
            }
          }
        }
      }
    }
    when {
      countriesState.isError -> {
        countriesState.error {
          CommonError(
            modifier = Modifier.visible(countriesState.isError),
            errorMessage = it.message.toString()
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryFilter(
  viewModel: MainViewModel,
  isEnabled: Boolean,
) {

  var filter by rememberSaveable { mutableStateOf(viewModel.filter.value) }

  Row(
    modifier = Modifier
      .padding(horizontal = 16.dp)
      .fillMaxWidth()
      .background(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = CircleShape
      ),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      modifier = Modifier.padding(start = 8.dp),
      imageVector = Icons.Filled.Search,
      tint = MaterialTheme.colorScheme.outline,
      contentDescription = null
    )
    TextField(
      modifier = Modifier
        .weight(1f),
      enabled = isEnabled,
      colors = TextFieldDefaults.textFieldColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
      ),
      shape = CircleShape,
      placeholder = { Text(text = "Search a Country") },
      value = filter,
      onValueChange = {
        filter = it
        viewModel.setFilter(it)
      }
    )
  }
}

@Composable
fun CountryItemPlaceholder() {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .height(256.dp)
      .padding(bottom = 8.dp)
      .placeholder(
        visible = true,
        color = MaterialTheme.colorScheme.surfaceVariant,
        highlight = PlaceholderHighlight.shimmer()
      )
  ) {}
}

@Composable
fun CountryItem(
  name: String,
  iso2: String,
  country: Country,
  navigateToCountryDetail: (String, String) -> Unit
) {
  Card(
    modifier = Modifier
      .padding(bottom = 8.dp)
      .clickable {
        if (iso2.isNotBlank() && name.isNotBlank()) navigateToCountryDetail(name, iso2)
      },
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
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
      ) {
        Text(
          modifier = Modifier.padding(horizontal = 16.dp),
          text = country.name.replaceIfNull(),
          style = MaterialTheme.typography.headlineLarge
        )
      }
    }
  }
}