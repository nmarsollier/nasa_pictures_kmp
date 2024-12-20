package com.nmarsollier.nasapictures.ui.imagesList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.nmarsollier.nasapictures.common.ui.KoinPreview
import com.nmarsollier.nasapictures.models.api.images.ImageValue
import com.nmarsollier.nasapictures.res.AppColors
import com.nmarsollier.nasapictures.res.AppStrings
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@ExperimentalFoundationApi
fun ImageItemContent(
    image: ImageValue,
    imageLoader: ImageLoader = koinInject(),
    imageRequest: ImageRequest.Builder = koinInject(),
    reduce: (ImagesListAction) -> Unit,
) {
    Card(shape = RoundedCornerShape(10.dp),
        backgroundColor = AppColors.BlackBackground,
        modifier = Modifier
            .size(165.dp)
            .combinedClickable(onClick = {})
            .clickable {
                reduce(ImagesListAction.GoPreview(image))
            }) {

        SubcomposeAsyncImage(
            modifier = Modifier
                .padding(16.dp)
                .height(165.dp)
                .width(165.dp),
            model = imageRequest.data(image.downloadUrl)
                .crossfade(true).build(),
            imageLoader = imageLoader,
            contentDescription = null,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(16.dp),
                    color = AppColors.TextWhite
                )
            },
            onSuccess = {
                reduce(ImagesListAction.UpdateDate)
            },
        )

        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)
        ) {
            Text(
                text = image.identifier,
                fontSize = 10.sp,
                color = AppColors.TextWhite
            )
            Text(
                text = "${AppStrings.captured} ${image.formattedHourMinute}hs",
                fontSize = 10.sp,
                color = AppColors.TextWhite
            )
        }

    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun ImageItemViewPreview() {
    KoinPreview {
        Column {
            ImageItemContent(
                ImageValue.Samples.simpleImageValeSample
            ) {}
        }
    }
}
