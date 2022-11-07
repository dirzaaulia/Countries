package com.dirzaaulia.countries.ui.country.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dirzaaulia.countries.data.model.Country
import com.dirzaaulia.countries.ui.common.StaggeredVerticalGrid
import com.dirzaaulia.countries.utils.NetworkImage
import com.dirzaaulia.countries.utils.unicodeEmojiToHtmlEmoji
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlin.random.Random

@Composable
fun CountryInformationTab(
    modifier: Modifier = Modifier,
    country: Country?,
    timezonesMap: Map<String, String>?,
    translationsMap: Map<String, String>?,
    isPlaceholder: Boolean
) {
    LazyColumn {
        item {
            StaggeredVerticalGrid(
                maxColumnWidth = 220.dp,
                modifier = modifier.padding(4.dp)
            ) {
                if (isPlaceholder) {
                    repeat(20) {
                        CountryInformationItemPlaceholder()
                    }
                } else {
                    country?.let {
                        Country.setDataMap(it).map { map ->
                            CountryInformationItem(map = map)
                        }
                    }
                    timezonesMap?.map { map ->
                        CountryInformationItem(map = map)
                    }
                    translationsMap?.map { map ->
                        CountryInformationItem(map = map)
                    }
                }
            }
        }
    }
}

@Composable
fun CountryInformationItem(map: Map.Entry<String, String>) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column (
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = map.key,
                style = MaterialTheme.typography.headlineMedium
            )

            if (map.key == "Flag") {
                Card {
                    NetworkImage(url = map.value)
                }
            } else {
                val formattedValue = if (map.value.contains("U+")) {
                    map.value.unicodeEmojiToHtmlEmoji().toString()
                } else {
                    map.value
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
fun CountryInformationItemPlaceholder() {
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