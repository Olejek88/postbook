package de.olegrom.postbook.presentation.ui.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.olegrom.postbook.domain.usecase.GetPostUseCase
import de.olegrom.postbook.domain.usecase.GetPostsUseCase
import de.olegrom.postbook.domain.util.asResult
import de.olegrom.postbook.presentation.ui.main.ScreenState
import de.olegrom.postbook.domain.util.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ScreenState>(ScreenState.Idle)
    var state = _state.asStateFlow()
    fun getPostById(postId: Int) {
        viewModelScope.launch {
            getPostUseCase.invoke(id = postId).asResult().collectLatest { result ->
                setState(result)
            }
        }
    }

    fun getPostsByUserId(userId: Int) {
        viewModelScope.launch {
            getPostsUseCase.invoke(userId).asResult().collectLatest { result ->
                setState(result)
            }
        }
    }

    private fun setState(result: Result<Any>) {
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
                _state.update {
                    ScreenState.Success(result.data)
                }
            }
        }
    }
}
