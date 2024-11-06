package com.nmarsollier.nasapictures.ui.imageAnimation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import coil3.Bitmap
import coil3.compose.AsyncImage
import com.nmarsollier.nasapictures.common.ui.EmptyView
import com.nmarsollier.nasapictures.common.ui.KoinPreview
import com.nmarsollier.nasapictures.common.ui.LoadingView
import com.nmarsollier.nasapictures.models.extendedDate.ExtendedDateValue
import com.nmarsollier.nasapictures.res.AppColors
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ImageAnimationScreen(
    date: ExtendedDateValue, viewModel: ImageAnimationViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState(viewModel.viewModelScope.coroutineContext)

    DisposableEffect(date) {
        viewModel.reduce(ImageAnimationAction.FetchImages(date))
        onDispose { }
    }

    Column(
        modifier = Modifier
            .background(AppColors.BlueBackground)
            .fillMaxSize()
    ) {
        when (val st = state) {
            is ImageAnimationState.Ready -> AnimatedPreviewContent(st)
            ImageAnimationState.Error -> EmptyView()
            else -> LoadingView()
        }
    }
}

@Composable
fun BitmapAnimation(bitmaps: List<Bitmap>, frameDuration: Long = 50) {
    var currentFrame by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(frameDuration)
            currentFrame = (currentFrame + 1) % bitmaps.size
        }
    }

    AsyncImage(
        modifier = Modifier
            .padding(16.toDp())
            .height(600.toDp())
            .width(600.toDp()),
        model = bitmaps[currentFrame],
        contentDescription = null
    )
}

@Composable
fun AnimatedPreviewContent(state: ImageAnimationState.Ready) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BlackBackground)
    ) {
        BitmapAnimation(state.bitmaps)
    }
}

@Preview()
@Composable
fun OptionsViewPreview() {
    KoinPreview {
        Column {
            AnimatedPreviewContent(
                ImageAnimationState.Ready(
                    bitmaps = emptyList()
                )
            )
        }
    }
}

@Composable
fun Int.toDp(): Dp {
    val density = LocalDensity.current.density
    return (this / density).dp
}