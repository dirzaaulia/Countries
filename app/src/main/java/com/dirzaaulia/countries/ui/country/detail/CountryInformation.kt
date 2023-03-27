package com.dirzaaulia.countries.ui.country.detail

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dirzaaulia.countries.data.model.Country
import com.dirzaaulia.countries.ui.common.CommonError
import com.dirzaaulia.countries.utils.ResponseResult
import com.dirzaaulia.countries.utils.error
import com.dirzaaulia.countries.utils.isError

@Composable
fun CountryInformationTab(
    modifier: Modifier = Modifier,
    country: Country?,
    timezonesMap: Map<String, String>?,
    translationsMap: Map<String, String>,
    responseResult: ResponseResult<Country?>,
    retry: () -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(count = 2),
    ) {
        when (responseResult) {
            ResponseResult.Loading -> items(20) {
                CountryDetailItemPlaceholder()
            }

            is ResponseResult.Success -> {
                country?.let { country ->
                    items(Country.setDataMap(country).toList()) { item ->
                        CountryDetailItem(data = item)
                    }
                }
                items(timezonesMap?.toList() ?: emptyList()) { item ->
                    CountryDetailItem(data = item)
                }
                items(translationsMap.toList()) { item ->
                    CountryDetailItem(data = item)
                }
            }

            else -> Unit
        }
    }
    when {
        responseResult.isError -> {
            responseResult.error { throwable ->
                CommonError(
                    errorMessage = throwable.message.toString(),
                    retry = retry
                )
            }
        }
    }
}