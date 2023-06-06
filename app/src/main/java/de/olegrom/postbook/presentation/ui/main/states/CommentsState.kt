package de.olegrom.postbook.presentation.ui.main.states

import de.olegrom.postbook.domain.domain_model.CommentDomainModel

sealed interface CommentsState{
    object Loading: CommentsState
    object Idle : CommentsState
    data class Success(val comments: List<CommentDomainModel>) : CommentsState
    data class Error(val errorMessage: String) : CommentsState
}
