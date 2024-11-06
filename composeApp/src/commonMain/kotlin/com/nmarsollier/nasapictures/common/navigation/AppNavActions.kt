package com.nmarsollier.nasapictures.common.navigation

import androidx.navigation.NavHostController
import com.nmarsollier.nasapictures.common.utils.toJson
import com.nmarsollier.nasapictures.models.api.images.ImageValue
import com.nmarsollier.nasapictures.models.extendedDate.ExtendedDateValue

class AppNavActions(
    val navController: NavHostController,
) {
    fun goUp() {
        navController.navigateUp()
    }

    fun goImagesList(date: ExtendedDateValue) {
        navController.navigate("${AppGraph.ImagesScreen.name}/${date.toJson()}") {
            launchSingleTop = true
        }
    }

    fun goImagePreview(image: ImageValue) {
        navController.navigate("${AppGraph.ImagePreview.name}/${image.toJson()}") {
            launchSingleTop = true
        }
    }

    fun goImageAnimation(date: ExtendedDateValue) {
        navController.navigate("${AppGraph.ImageAnimation.name}/${date.toJson()}") {
            launchSingleTop = true
        }
    }
}
