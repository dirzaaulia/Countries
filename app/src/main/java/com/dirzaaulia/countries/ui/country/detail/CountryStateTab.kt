package com.dirzaaulia.countries.ui.country.detail

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dirzaaulia.countries.data.model.State
import com.dirzaaulia.countries.ui.common.CommonError
import com.dirzaaulia.countries.utils.ResponseResult
import com.dirzaaulia.countries.utils.error
import com.dirzaaulia.countries.utils.isError
import com.dirzaaulia.countries.utils.replaceIfNull

@Composable
fun CountryStateTab(
    modifier: Modifier = Modifier,
    stateList: List<State>,
    responseResult: ResponseResult<List<State>>,
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

            is ResponseResult.Success ->
                items(stateList) { item ->
                    CountryDetailItem(value = item.name.replaceIfNull())
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