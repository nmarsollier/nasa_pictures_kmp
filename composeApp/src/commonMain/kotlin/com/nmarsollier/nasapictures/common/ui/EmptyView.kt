package com.nmarsollier.nasapictures.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmarsollier.nasapictures.res.AppColors
import com.nmarsollier.nasapictures.res.AppStrings
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmptyView() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BlueBackground)
    ) {

        Text(
            text = AppStrings.noImagesLoaded,
            color = AppColors.TextWhite,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview()
@Composable
fun EmptyViewPreview() {
    EmptyView()
}

