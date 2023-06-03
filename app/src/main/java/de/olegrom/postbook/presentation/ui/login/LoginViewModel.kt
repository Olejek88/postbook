package de.olegrom.postbook.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.olegrom.postbook.domain.domain_model.UserDomainModel
import de.olegrom.postbook.domain.usecase.GetUserUseCase
import de.olegrom.postbook.domain.util.asResult
import de.olegrom.postbook.presentation.ui.main.ScreenState
import de.olegrom.postbook.domain.util.Result
import de.olegrom.postbook.domain.util.asCommonFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    private val _state = MutableStateFlow<ScreenState>(ScreenState.Idle)
    var state = _state.asStateFlow()
    fun getUserById(id: Int) {
        viewModelScope.launch {
            getUserUseCase.invoke(id = id).asResult().collectLatest { result ->
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
                            _state.update {
                                ScreenState.Success(result.data)
                            }
                        }
                    }
                }
            }
        }
    }
}
