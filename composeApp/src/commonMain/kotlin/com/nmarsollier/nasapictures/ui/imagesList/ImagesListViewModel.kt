package com.nmarsollier.nasapictures.ui.imagesList

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.nmarsollier.nasapictures.common.vm.StateViewModel
import com.nmarsollier.nasapictures.models.api.dates.refresh
import com.nmarsollier.nasapictures.models.api.images.ImageValue
import com.nmarsollier.nasapictures.common.ui.CoilUtils
import com.nmarsollier.nasapictures.models.extendedDate.ExtendedDateValue
import com.nmarsollier.nasapictures.models.useCases.FetchImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

sealed interface ImagesListEvent {
    @Stable
    data class GoPreview(val image: ImageValue) : ImagesListEvent

    @Stable
    data class GoAnimate(val date: ExtendedDateValue) : ImagesListEvent
}

sealed interface ImagesListState {
    @Stable
    data object Loading : ImagesListState

    @Stable
    data object Error : ImagesListState

    @Stable
    data class Ready(
        val date: ExtendedDateValue?, val images: List<ImageValue>
    ) : ImagesListState
}

sealed interface ImagesListAction {
    @Stable
    data class FetchImages(val date: ExtendedDateValue?) : ImagesListAction

    @Stable
    data class GoPreview(val image: ImageValue) : ImagesListAction

    @Stable
    data class GoAnimate(val date: ExtendedDateValue) : ImagesListAction

    @Stable
    data object UpdateDate : ImagesListAction
}

class ImagesListViewModel(
    private val coilUtils: CoilUtils,
    private val fetchImagesUseCase: FetchImagesUseCase,
) : StateViewModel<ImagesListState, ImagesListEvent, ImagesListAction>(ImagesListState.Loading) {

    override fun reduce(action: ImagesListAction) {
        when (action) {
            is ImagesListAction.FetchImages -> fetchImages(action.date)
            is ImagesListAction.GoPreview -> ImagesListEvent.GoPreview(action.image).sendToEvent()
            is ImagesListAction.GoAnimate -> ImagesListEvent.GoAnimate(action.date).sendToEvent()
            ImagesListAction.UpdateDate -> updateDate()
        }
    }

    private fun fetchImages(date: ExtendedDateValue?) = viewModelScope.launch(Dispatchers.IO) {
        ImagesListState.Loading.sendToState()

        val queryDate = date?.date ?: run {
            ImagesListState.Error.sendToState()
            return@launch
        }

        try {
            ImagesListState.Ready(
                images = fetchImagesUseCase.fetchImages(queryDate),
                date = date.refresh(coilUtils),
            )
        } catch (e: Exception) {
            ImagesListState.Error
        }.sendToState()
    }

    private fun updateDate() = MainScope().launch(Dispatchers.IO) {
        (state.value as? ImagesListState.Ready)?.let {
            it.copy(
                date = it.date?.refresh(coilUtils)
            ).sendToState()
        }
    }
}
