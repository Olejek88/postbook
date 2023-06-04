package de.olegrom.postbook.presentation.ui.posts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.olegrom.postbook.domain.domain_model.PostDomainModel
import de.olegrom.postbook.presentation.navigation.main.Screen
import de.olegrom.postbook.presentation.ui.common.ErrorWidget
import de.olegrom.postbook.presentation.ui.common.PageLoadingView
import de.olegrom.postbook.presentation.ui.main.ScreenState
import de.olegrom.postbook.presentation.ui.main.TopAppBarViewModel
import de.olegrom.postbook.presentation.ui.posts.widgets.PostCard
import kotlinx.coroutines.flow.update
import org.koin.androidx.compose.getViewModel

@Composable
fun PostsScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: PostsViewModel = getViewModel(),
    topAppBarViewModel: TopAppBarViewModel = getViewModel()
) {
    val state by viewModel.postsState.collectAsState()
    topAppBarViewModel.title.update { "Posts" }
    viewModel.getPostsByUserId(1)
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is ScreenState.Error -> ErrorWidget((state as ScreenState.Error).errorMessage)
            ScreenState.Idle -> {}
            ScreenState.Loading -> PageLoadingView(modifier)
            is ScreenState.Success -> {
                val posts = (state as ScreenState.Success).entity as List<PostDomainModel>
                PageContent(modifier, posts, navController)
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
            PostCard(
                item.title,
                item.body
            ) {
                navController.navigate(
                    Screen.Post.route.replace("{postId}", item.id.toString())
                )
            }
        }
    }
}