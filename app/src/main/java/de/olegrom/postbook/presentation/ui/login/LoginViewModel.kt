package de.olegrom.postbook.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.olegrom.postbook.data.preferences.SharedPreferenceHelper
import de.olegrom.postbook.domain.domain_model.UserDomainModel
import de.olegrom.postbook.domain.usecase.GetUserUseCase
import de.olegrom.postbook.domain.util.asResult
import de.olegrom.postbook.presentation.ui.main.ScreenState
import de.olegrom.postbook.domain.util.Result
import de.olegrom.postbook.domain.util.asCommonFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(private val getUserUseCase: GetUserUseCase,
                     val preferences: SharedPreferenceHelper
) : ViewModel() {
    private val _state = MutableStateFlow<ScreenState>(ScreenState.Idle)
    var state = _state.asStateFlow()
    fun getUserById(id: Int) {
        viewModelScope.launch {
            getUserUseCase.invoke(id = id).asResult().collectLatest { result ->
                Timber.i("res=${result}")
                when (result) {
                    is Result.Error -> {
                        _state.update {
                            ScreenState.Error(result.exception.message)
                        }
                    }
                    is Result.Idle -> {}
                    is Result.Loading -> {
                        _state.update {
                            ScreenState.Loading
                        }
                    }
                    is Result.Success -> {
                        if (result.data is UserDomainModel) {
                            preferences.setUserId(result.data.id)
                            _state.update {
                                ScreenState.Success(result.data)
                            }
                        } else {
                            _state.update {
                                ScreenState.Error("User with this ID doesn't exist")
                            }
                        }
                    }
                }
            }
        }
    }
}
