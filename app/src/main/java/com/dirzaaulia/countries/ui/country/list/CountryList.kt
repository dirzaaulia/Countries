package com.dirzaaulia.countries.ui.country.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dirzaaulia.countries.R
import com.dirzaaulia.countries.data.model.Country
import com.dirzaaulia.countries.ui.common.CommonError
import com.dirzaaulia.countries.ui.common.NetworkImage
import com.dirzaaulia.countries.ui.main.MainViewModel
import com.dirzaaulia.countries.utils.error
import com.dirzaaulia.countries.utils.isError
import com.dirzaaulia.countries.utils.isLoading
import com.dirzaaulia.countries.utils.isSucceeded
import com.dirzaaulia.countries.utils.replaceIfNull
import com.dirzaaulia.countries.utils.success
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlinx.coroutines.launch

@Composable
fun CountryList(
    viewModel: MainViewModel,
    navigateToCountryDetail: (String, String) -> Unit,
) {

    val countriesState by viewModel.countriesState.collectAsState()
    val filter by viewModel.filter.collectAsState()
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    scope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowUpward,
                    contentDescription = ""
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .statusBarsPadding()
        ) {
            CountryFilter(
                viewModel = viewModel,
                isEnabled = countriesState.isSucceeded,
            )
            LazyColumn(
                modifier = Modifier.padding(top = 16.dp),
                state = lazyListState
            ) {
                when {
                    countriesState.isLoading -> {
                        items(10) {
                            CountryItemPlaceholder()
                        }
                    }

                    countriesState.isSucceeded -> {
                        countriesState.success { data ->
                            val filteredCountry = if (filter.isBlank()) data
                            else {
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
                            errorMessage = it.message.toString(),
                            retry = { viewModel.getCountries() }
                        )
                    }
                }
            }
        }
    }
}

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
            placeholder = { Text(text = stringResource(R.string.search_a_country)) },
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
                url = "https://flagcdn.com/w320/${country.iso2?.lowercase()}.png",
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