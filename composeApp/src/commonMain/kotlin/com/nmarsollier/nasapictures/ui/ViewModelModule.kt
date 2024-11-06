package com.nmarsollier.nasapictures.ui

import com.nmarsollier.nasapictures.ui.home.HomeScreenUpdater
import com.nmarsollier.nasapictures.ui.home.HomeViewModel
import com.nmarsollier.nasapictures.ui.imageAnimation.ImageAnimationViewModel
import com.nmarsollier.nasapictures.ui.imagePreview.ImagePreviewViewModel
import com.nmarsollier.nasapictures.ui.imagesList.ImagesListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val koinViewModelModule = module {
    viewModelOf(::ImageAnimationViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ImagePreviewViewModel)
    viewModelOf(::ImagesListViewModel)
    singleOf(::HomeScreenUpdater)
}
