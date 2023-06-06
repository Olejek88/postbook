package de.olegrom.postbook.presentation.ui.main.states

import de.olegrom.postbook.domain.domain_model.PostDomainModel

sealed interface PostsState{
    object Loading: PostsState
    object Idle : PostsState
    data class Success(val posts: List<PostDomainModel>) : PostsState
    data class Error(val errorMessage: String) : PostsState
}
