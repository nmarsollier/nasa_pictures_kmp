package com.nmarsollier.nasapictures.ui.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.nmarsollier.nasapictures.common.ui.CoilUtils
import com.nmarsollier.nasapictures.common.vm.StateViewModel
import com.nmarsollier.nasapictures.models.api.dates.asDateValue
import com.nmarsollier.nasapictures.models.api.dates.asExtendedDateValue
import com.nmarsollier.nasapictures.models.database.dates.DatesEntity
import com.nmarsollier.nasapictures.models.database.dates.DatesEntityDao
import com.nmarsollier.nasapictures.models.extendedDate.ExtendedDateValue
import com.nmarsollier.nasapictures.models.useCases.FetchDatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

sealed interface MainEvent {
    @Stable
    data class GoImages(val date: ExtendedDateValue) : MainEvent
}

sealed interface HomeState {
    @Stable
    data object Loading : HomeState

    @Stable
    data object Error : HomeState

    @Stable
    data class Ready(
        val dates: List<ExtendedDateValue>,
    ) : HomeState
}

sealed interface MainAction {
    @Stable
    data object RefreshDates : MainAction

    @Stable
    data class GoImages(val date: ExtendedDateValue) : MainAction
}

class HomeViewModel(
    private val dateDao: DatesEntityDao,
    private val coilUtils: CoilUtils,
    private val fetchDatesUseCase: FetchDatesUseCase,
    private val homeScreenUpdater: HomeScreenUpdater,
) : StateViewModel<HomeState, MainEvent, MainAction>(HomeState.Loading) {
    init {
        loadDates()

        viewModelScope.launch(Dispatchers.IO) {
            fetchDatesUseCase.updateFlow.collect {
                reduce(MainAction.RefreshDates)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            homeScreenUpdater.updateFlow.collect {
                reduce(MainAction.RefreshDates)
            }
        }
    }

    override fun reduce(action: MainAction) {
        when (action) {
            MainAction.RefreshDates -> loadDates()
            is MainAction.GoImages -> MainEvent.GoImages(action.date).sendToEvent()
        }
    }

    private fun loadDates() = viewModelScope.launch(Dispatchers.IO) {
        HomeState.Ready(dateDao.findAll(0, PAGE_SIZE).asResultPage()).sendToState()
    }

    private suspend fun List<DatesEntity>?.asResultPage(): List<ExtendedDateValue> =
        (this ?: emptyList()).let {
            it.map { entity -> entity.date.asDateValue.asExtendedDateValue(coilUtils) }
        }
}

private const val PAGE_SIZE = 30;
