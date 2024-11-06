package com.nmarsollier.nasapictures.ui.imagePreview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.nmarsollier.nasapictures.common.ui.LoadingView
import com.nmarsollier.nasapictures.models.api.images.ImageValue
import com.nmarsollier.nasapictures.res.AppColors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ImagePreviewScreen(
    imageValue: ImageValue,
    viewModel: ImagePreviewViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState(viewModel.viewModelScope.coroutineContext)

    DisposableEffect(imageValue) {
        viewModel.reduce(ImagePreviewAction.Init(imageValue))
        onDispose { }
    }

    Scaffold(
        floatingActionButton = {
            if ((state as? ImagePreviewState.Ready)?.showDetails == false) {
                FloatingActionButton(
                    onClick = {
                        viewModel.reduce(ImagePreviewAction.ToggleDetails)
                    },
                    shape = CircleShape,
                    backgroundColor = AppColors.LightBlueBackground
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        "",
                        tint = AppColors.TextWhite
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End, modifier = Modifier
            .background(AppColors.BlackBackground)
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(AppColors.BlackBackground)
                .fillMaxSize()
        ) {
            when (val st = state) {
                is ImagePreviewState.Ready -> ImagePreviewContent(
                    st,
                    reduce = viewModel::reduce
                )

                else -> LoadingView()
            }
        }
    }
}
