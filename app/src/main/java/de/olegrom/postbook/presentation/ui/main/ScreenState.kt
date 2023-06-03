package de.olegrom.postbook.presentation.ui.main

sealed interface ScreenState{
    object Loading: ScreenState
    object Idle : ScreenState
    data class Success(val entity: Any) : ScreenState
    data class Error(val errorMessage: String) : ScreenState
}
