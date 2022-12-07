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
                        CountryDetailItemPlaceholder()
                    }
                } else {
                    country?.let {
                        Country.setDataMap(it).forEach { map ->
                            CountryDetailItem(map = map)
                        }
                    }
                    timezonesMap?.forEach { map ->
                        CountryDetailItem(map = map)
                    }
                    translationsMap?.forEach { map ->
                        CountryDetailItem(map = map)
                    }
                }
            }
        }
    }
}