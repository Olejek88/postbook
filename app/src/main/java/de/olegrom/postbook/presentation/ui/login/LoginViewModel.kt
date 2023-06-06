package de.olegrom.postbook.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.olegrom.postbook.data.preferences.SharedPreferenceHelper
import de.olegrom.postbook.domain.domain_model.UserDomainModel
import de.olegrom.postbook.domain.usecase.GetUserUseCase
import de.olegrom.postbook.domain.util.asResult
import de.olegrom.postbook.presentation.ui.main.states.LoginState
import de.olegrom.postbook.domain.util.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(private val getUserUseCase: GetUserUseCase,
                     private val preferences: SharedPreferenceHelper? = null
) : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    var state = _state.asStateFlow()
    fun getUserById(id: Int) {
        viewModelScope.launch {
            getUserUseCase.invoke(id = id).asResult().collectLatest { result ->
                Timber.i("res=${result}")
                when (result) {
                    is Result.Error -> {
                        _state.update {
                            LoginState.Error(result.exception.message)
                        }
                    }
                    is Result.Loading -> {
                        _state.update {
                            LoginState.Loading
                        }
                    }
                    is Result.Success -> {
                        if (result.data is UserDomainModel) {
                            preferences?.setUserId(result.data.id)
                            _state.update {
                                Timber.i("update state")
                                LoginState.Success(result.data)
                            }
                        } else {
                            _state.update {
                                LoginState.Error("User with this ID doesn't exist")
                            }
                        }
                    }
                    is Result.Idle -> {}
                }
            }
        }
    }
}
