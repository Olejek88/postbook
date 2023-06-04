package de.olegrom.postbook.presentation.ui.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.olegrom.postbook.domain.usecase.GetCommentsUseCase
import de.olegrom.postbook.domain.usecase.GetPostUseCase
import de.olegrom.postbook.domain.usecase.GetPostsUseCase
import de.olegrom.postbook.domain.util.asResult
import de.olegrom.postbook.presentation.ui.main.ScreenState
import de.olegrom.postbook.domain.util.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {
    private val _postsState = MutableStateFlow<ScreenState>(ScreenState.Idle)
    var postsState = _postsState.asStateFlow()
    private val _commentsState = MutableStateFlow<ScreenState>(ScreenState.Idle)
    var commentsState = _commentsState.asStateFlow()
    fun getPostById(postId: Int) {
        viewModelScope.launch {
            getPostUseCase.invoke(id = postId).asResult().collectLatest { result ->
                setState(result, _postsState)
            }
        }
    }

    fun getCommentsByPostId(postId: Int) {
        viewModelScope.launch {
            getCommentsUseCase.invoke(postId).asResult().collectLatest { result ->
                setState(result, _commentsState)
            }
        }
    }

    fun getPostsByUserId(userId: Int) {
        viewModelScope.launch {
            getPostsUseCase.invoke(userId).asResult().collectLatest { result ->
                setState(result, _postsState)
            }
        }
    }

    private fun setState(result: Result<Any>, state: MutableStateFlow<ScreenState>) {
        when (result) {
            is Result.Error -> {
                state.update {
                    ScreenState.Error(result.exception.message)
                }
            }
            is Result.Idle -> {}
            is Result.Loading -> {
                state.update {
                    ScreenState.Loading
                }
            }
            is Result.Success -> {
                state.update {
                    ScreenState.Success(result.data)
                }
            }
        }
    }
}
