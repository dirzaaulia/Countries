package com.dirzaaulia.countries.ui.country.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dirzaaulia.countries.data.model.State
import com.dirzaaulia.countries.ui.common.CommonError
import com.dirzaaulia.countries.ui.common.StaggeredVerticalGrid
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
    LazyColumn {
        item {
            StaggeredVerticalGrid(
                maxColumnWidth = 220.dp,
                modifier = modifier.padding(4.dp)
            ) {
                when (responseResult) {
                    ResponseResult.Loading ->  repeat(20) {
                        CountryDetailItemPlaceholder()
                    }
                    is ResponseResult.Success ->  stateList.forEach {
                        CountryDetailItem(value = it.name.replaceIfNull())
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