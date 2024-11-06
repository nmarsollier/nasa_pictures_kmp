package com.nmarsollier.nasapictures.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nmarsollier.nasapictures.res.AppColors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DatesListContent(
    state: HomeState.Ready, reduce: (MainAction) -> Unit,
) {
    val dates = state.dates

    LazyColumn(
        modifier = Modifier
            .background(AppColors.BlueBackground)
            .fillMaxSize()
    ) {
        items(
            count = dates.size,
            key = { dates[it].date }
        ) { index ->
            dates[index].let {
                DateItemView(it, reduce)
            }
        }
    }
}
