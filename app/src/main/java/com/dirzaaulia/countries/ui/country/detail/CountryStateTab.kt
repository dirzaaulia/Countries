package com.dirzaaulia.countries.ui.country.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dirzaaulia.countries.data.model.Country
import com.dirzaaulia.countries.data.model.State
import com.dirzaaulia.countries.ui.common.StaggeredVerticalGrid
import com.dirzaaulia.countries.utils.replaceIfNull

@Composable
fun CountryStateTab(
    modifier: Modifier = Modifier,
    stateList: List<State>,
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
                    stateList.forEach { 
                        CountryDetailItem(value = it.name.replaceIfNull())
                    }
                }
            }
        }
    }
}