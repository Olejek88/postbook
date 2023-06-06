package de.olegrom.postbook.presentation.ui.main.states

import de.olegrom.postbook.domain.domain_model.UserDomainModel

sealed interface LoginState{
    object Loading: LoginState
    object Idle : LoginState
    data class Success(val user: UserDomainModel) : LoginState
    data class Error(val errorMessage: String) : LoginState
}
