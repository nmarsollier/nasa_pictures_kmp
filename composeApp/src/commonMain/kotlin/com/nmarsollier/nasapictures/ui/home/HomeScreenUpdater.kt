package com.nmarsollier.nasapictures.ui.home

import com.nmarsollier.nasapictures.common.utils.StateEventObject

enum class MainScreenState {
    Update
}

class HomeScreenUpdater : StateEventObject<MainScreenState>() {
    fun updateScreen() {
        MainScreenState.Update.sendToEvent()
    }
}