package de.olegrom.postbook.presentation.ui.posts

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.olegrom.postbook.domain.domain_model.CommentDomainModel
import de.olegrom.postbook.domain.domain_model.PostDomainModel
import de.olegrom.postbook.presentation.ui.common.ErrorWidget
import de.olegrom.postbook.presentation.ui.common.PageLoadingView
import de.olegrom.postbook.presentation.ui.main.ScreenState
import de.olegrom.postbook.presentation.ui.main.TopAppBarViewModel
import de.olegrom.postbook.presentation.ui.posts.widgets.CommentCard
import de.olegrom.postbook.presentation.ui.posts.widgets.PostCard
import kotlinx.coroutines.flow.update
import org.koin.androidx.compose.getViewModel

@Composable
fun PostScreen(
    modifier: Modifier,
    postId: Int,
    viewModel: PostsViewModel = getViewModel(),
    topAppBarViewModel: TopAppBarViewModel = getViewModel()
) {
    val postsState by viewModel.postsState.collectAsState()
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
        when (postsState) {
            is ScreenState.Error -> ErrorWidget((postsState as ScreenState.Error).errorMessage)
            ScreenState.Idle -> {}
            ScreenState.Loading -> PageLoadingView(modifier)
            is ScreenState.Success -> {
                val post = (postsState as ScreenState.Success).entity as PostDomainModel
                topAppBarViewModel.title.update { post.title }
                PostCard(title = post.title, subtitle = post.body)
            }
        }
        when (commentsState) {
            is ScreenState.Error -> ErrorWidget((commentsState as ScreenState.Error).errorMessage)
            ScreenState.Idle -> {}
            ScreenState.Loading -> PageLoadingView(modifier)
            is ScreenState.Success -> {
                val comments = (commentsState as ScreenState.Success).entity as List<CommentDomainModel>
                comments.forEach {
                    CommentCard(it)
                }
            }
        }
    }
}
