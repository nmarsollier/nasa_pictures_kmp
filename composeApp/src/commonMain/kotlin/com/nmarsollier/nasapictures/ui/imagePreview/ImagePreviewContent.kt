package com.nmarsollier.nasapictures.ui.imagePreview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.Bitmap
import com.nmarsollier.nasapictures.common.ui.CoilUtils
import com.nmarsollier.nasapictures.common.ui.KoinPreview
import com.nmarsollier.nasapictures.models.api.images.ImageValue
import com.nmarsollier.nasapictures.res.AppColors
import com.nmarsollier.nasapictures.res.AppStrings
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun ImagePreviewContent(
    image: ImagePreviewState.Ready,
    coilUtils: CoilUtils = koinInject(),
    reduce: (ImagePreviewAction) -> Unit,
) {
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(image.imageValue.downloadUrl) {
        imageBitmap = coilUtils.loadImage(image.imageValue.downloadUrl)
    }
    val imageBm = imageBitmap

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BlackBackground)
    ) {
        if (imageBm != null) {
            ZoomableImage(
                modifier = Modifier.fillMaxSize(),
                imageBitmap = imageBm
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .padding(16.dp),
                color = AppColors.TextWhite
            )
        }

        if (image.showDetails) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(AppColors.BlueBackground)
            ) {
                ImageSheet(image = image.imageValue, reduce)
            }
        }
    }
}

@Composable
fun ImageSheet(image: ImageValue, reduce: (ImagePreviewAction) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = AppStrings.imageDetails,
                fontSize = 20.sp,
                color = AppColors.TextWhite
            )
            Spacer(Modifier.weight(1f))
            IconButton(
                modifier = Modifier.background(AppColors.BlueCardBackground, shape = CircleShape),
                onClick = {
                    reduce(ImagePreviewAction.ToggleDetails)
                }
            ) {
                Icon(rememberVectorPainter(Icons.Filled.Close), "", tint = AppColors.TextWhite)
            }
        }
        Text(
            text = AppStrings.imageNumber,
            fontSize = 16.sp,
            color = AppColors.TextColorLightGray,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = image.identifier,
            fontSize = 18.sp,
            color = AppColors.TextWhite,
            modifier = Modifier.padding(top = 2.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = AppStrings.capture,
                    fontSize = 14.sp,
                    color = AppColors.TextColorLightGray
                )
                Text(
                    text = image.formattedHourMinute,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextWhite,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Column {
                Text(
                    text = AppStrings.latitude,
                    fontSize = 14.sp,
                    color = AppColors.TextColorLightGray
                )
                Text(
                    text = image.coordinates.lat.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextWhite,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Column {
                Text(
                    text = AppStrings.longitude,
                    fontSize = 14.sp,
                    color = AppColors.TextColorLightGray
                )
                Text(
                    text = image.coordinates.lon.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextWhite,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

    }
}

@Preview
@Composable
fun OptionsViewPreview() {
    KoinPreview {
        Column {
            ImagePreviewContent(
                ImagePreviewState.Ready(
                    imageValue = ImageValue.Samples.simpleImageValeSample,
                    showDetails = false
                )
            ) { }
        }
    }
}
