package de.olegrom.postbook.presentation.ui.main.states

import de.olegrom.postbook.domain.domain_model.PostDomainModel

sealed interface PostState{
    object Loading: PostState
    object Idle : PostState
    data class Success(val post: PostDomainModel) : PostState
    data class Error(val errorMessage: String) : PostState
}
