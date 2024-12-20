package com.nmarsollier.nasapictures.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nmarsollier.nasapictures.res.AppColors
import com.nmarsollier.nasapictures.res.AppStrings
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeMenu(
) {
    TopAppBar(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        AppColors.LightBlueBackground,
                        AppColors.BlueBackground
                    )
                )
            )
            .padding(top = 24.dp),
        elevation = 0.dp,
        title = { Text(AppStrings.appName) },
        backgroundColor = Color.Transparent,
        contentColor = AppColors.TextWhite,
    )
}

@Preview()
@Composable
fun MainMenuPreview() {
    MaterialTheme {
        Column {
            HomeMenu()
        }
    }
}
