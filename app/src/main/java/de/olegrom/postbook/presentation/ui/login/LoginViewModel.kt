package de.olegrom.postbook.presentation.ui.login

import androidx.lifecycle.ViewModel
import de.olegrom.postbook.domain.domain_model.UserDomainModel
import de.olegrom.postbook.domain.usecase.GetUserUseCase
import de.olegrom.postbook.domain.util.asResult
import de.olegrom.postbook.presentation.ui.main.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import de.olegrom.postbook.domain.util.Result
import de.olegrom.postbook.domain.util.asCommonFlow

class LoginViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    fun getUserById(id: Int): Flow<ScreenState> = flow {
        when (val result = getUserUseCase.invoke(id = id).asResult().last()) {
            is Result.Error -> {
                emit(ScreenState.Error(result.exception.message))
            }
            is Result.Idle -> {}
            is Result.Loading -> {}
            is Result.Success -> {
                if (result.data is UserDomainModel)
                    emit(ScreenState.Success(result.data))
            }
        }
    }.asCommonFlow()
}
