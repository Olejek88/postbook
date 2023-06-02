package de.olegrom.postbook.presentation.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class TopAppBarViewModel : ViewModel() {
    var title = MutableStateFlow("")
}
