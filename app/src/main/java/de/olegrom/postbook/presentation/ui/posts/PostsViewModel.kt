package de.olegrom.postbook.presentation.ui.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.olegrom.postbook.data.preferences.SharedPreferenceHelper
import de.olegrom.postbook.domain.usecase.GetCommentsUseCase
import de.olegrom.postbook.domain.usecase.GetPostUseCase
import de.olegrom.postbook.domain.usecase.GetPostsUseCase
import de.olegrom.postbook.domain.util.asResult
import de.olegrom.postbook.domain.util.Result
import de.olegrom.postbook.presentation.ui.main.states.CommentsState
import de.olegrom.postbook.presentation.ui.main.states.PostState
import de.olegrom.postbook.presentation.ui.main.states.PostsState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val preferences: SharedPreferenceHelper? = null
) : ViewModel() {
    private val _postDetailState = MutableStateFlow<PostState>(PostState.Idle)
    var postDetailState = _postDetailState.asStateFlow()
    private val _commentsState = MutableStateFlow<CommentsState>(CommentsState.Idle)
    var commentsState = _commentsState.asStateFlow()

    val postsState = MutableLiveData<PostsState>(PostsState.Idle)
    var showOnlyFavouritePosts: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getPostById(postId: Int) {
        viewModelScope.launch {
            getPostUseCase.invoke(id = postId).asResult().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _postDetailState.update {
                            PostState.Error(result.exception.message)
                        }
                    }
                    is Result.Idle -> {}
                    is Result.Loading -> {
                        _postDetailState.update {
                            PostState.Loading
                        }
                    }
                    is Result.Success -> {
                        _postDetailState.update {
                            PostState.Success(result.data)
                        }
                    }
                }
            }
        }
    }

    fun getCommentsByPostId(postId: Int) {
        viewModelScope.launch {
            getCommentsUseCase.invoke(postId).asResult().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _commentsState.update {
                            CommentsState.Error(result.exception.message)
                        }
                    }
                    is Result.Idle -> {}
                    is Result.Loading -> {
                        _commentsState.update {
                            CommentsState.Loading
                        }
                    }
                    is Result.Success -> {
                        _commentsState.update {
                            CommentsState.Success(result.data)
                        }
                    }
                }
            }
        }
    }

    fun getAllPosts(onlyFav: Boolean) {
        val userId = preferences?.getUserId() ?: 1
        viewModelScope.launch {
            getPostsUseCase.invoke(userId, onlyFav).asResult().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        postsState.postValue(PostsState.Error(result.exception.message))
                    }
                    is Result.Idle -> {}
                    is Result.Loading -> {
                        postsState.postValue(PostsState.Loading)
                    }
                    is Result.Success -> {
                        postsState.postValue(PostsState.Success(result.data))
                    }
                }
            }
        }
    }
}
