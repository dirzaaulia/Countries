package com.dirzaaulia.countries.ui.country.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dirzaaulia.countries.data.model.Country
import com.dirzaaulia.countries.ui.common.CommonError
import com.dirzaaulia.countries.ui.common.StaggeredVerticalGrid
import com.dirzaaulia.countries.utils.ResponseResult
import com.dirzaaulia.countries.utils.error
import com.dirzaaulia.countries.utils.isError

@Composable
fun CountryInformationTab(
    modifier: Modifier = Modifier,
    country: Country?,
    timezonesMap: Map<String, String>?,
    translationsMap: Map<String, String?>,
    responseResult: ResponseResult<Country?>,
    retry: () -> Unit
) {
    LazyColumn {
        item {
            StaggeredVerticalGrid(
                maxColumnWidth = 220.dp,
                modifier = modifier.padding(4.dp)
            ) {
                when (responseResult) {
                    ResponseResult.Loading -> repeat(20) {
                        CountryDetailItemPlaceholder()
                    }
                    is ResponseResult.Success -> {
                        country?.let {
                            Country.setDataMap(it).forEach { map ->
                                CountryDetailItem(map = map)
                            }
                        }
                        timezonesMap?.forEach { map ->
                            CountryDetailItem(map = map)
                        }
                        translationsMap.forEach { map ->
                            CountryDetailItem(map = map)
                        }
                    }

                    else -> {}
                }
            }
        }
        when {
            responseResult.isError -> {
                responseResult.error { throwable ->
                    item {
                        CommonError(
                            errorMessage = throwable.message.toString(),
                            retry = retry
                        )
                    }
                }
            }
        }
    }
}