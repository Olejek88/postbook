package de.olegrom.postbook.presentation.ui.posts

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.olegrom.postbook.presentation.ui.common.ErrorWidget
import de.olegrom.postbook.presentation.ui.common.PageLoadingView
import de.olegrom.postbook.presentation.ui.main.TopAppBarViewModel
import de.olegrom.postbook.presentation.ui.main.states.CommentsState
import de.olegrom.postbook.presentation.ui.main.states.PostState
import de.olegrom.postbook.presentation.ui.posts.widgets.CommentCard
import de.olegrom.postbook.presentation.ui.posts.widgets.PostCard
import kotlinx.coroutines.flow.update

@Composable
fun PostScreen(
    modifier: Modifier,
    postId: Int,
    viewModel: PostsViewModel,
    topAppBarViewModel: TopAppBarViewModel
) {
    val postState by viewModel.postDetailState.collectAsState()
    val commentsState by viewModel.commentsState.collectAsState()
    viewModel.getPostById(postId)
    viewModel.getCommentsByPostId(postId)
    Column(
        modifier = modifier
            .padding(10.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta -> delta }
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (postState) {
            is PostState.Error -> ErrorWidget((postState as PostState.Error).errorMessage)
            is PostState.Idle -> {}
            is PostState.Loading -> PageLoadingView(modifier)
            is PostState.Success -> {
                val post = (postState as PostState.Success).post
                topAppBarViewModel.title.update { post.title }
                PostCard(post)
            }
        }
        when (commentsState) {
            is CommentsState.Error -> {}
            is CommentsState.Idle -> {}
            is CommentsState.Loading -> PageLoadingView(modifier)
            is CommentsState.Success -> {
                val comments = (commentsState as CommentsState.Success).comments
                comments.forEach {
                    CommentCard(it)
                }
            }
        }
    }
}
