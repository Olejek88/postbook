package de.olegrom.postbook.presentation.ui.posts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.olegrom.postbook.domain.domain_model.PostDomainModel
import de.olegrom.postbook.presentation.navigation.main.Screen
import de.olegrom.postbook.presentation.ui.common.ErrorWidget
import de.olegrom.postbook.presentation.ui.common.PageLoadingView
import de.olegrom.postbook.presentation.ui.main.states.PostsState
import de.olegrom.postbook.presentation.ui.main.TopAppBarViewModel
import de.olegrom.postbook.presentation.ui.posts.widgets.PostCard
import kotlinx.coroutines.flow.update

@Composable
fun PostsScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: PostsViewModel,
    topAppBarViewModel: TopAppBarViewModel
) {
    val state by viewModel.postsState.observeAsState()
    topAppBarViewModel.title.update { "Posts" }
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is PostsState.Error -> ErrorWidget((state as PostsState.Error).errorMessage)
            is PostsState.Idle, null -> {}
            is PostsState.Loading -> PageLoadingView(modifier)
            is PostsState.Success -> {
                val postsEntity = (state as PostsState.Success).posts
                PageContent(modifier, postsEntity, navController)
            }
        }
    }
}

@Composable
fun PageContent(
    modifier: Modifier,
    posts: List<PostDomainModel>,
    navController: NavHostController
) {
    val listState: LazyListState = rememberLazyListState()
    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            count = posts.size,
        ) { index ->
            val item = posts[index]
            PostCard(item) {
                navController.navigate(
                    Screen.Post.route.replace("{postId}", item.id.toString())
                )
            }
        }
    }
}