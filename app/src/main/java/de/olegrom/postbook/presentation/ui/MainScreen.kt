package de.olegrom.postbook.presentation.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import de.olegrom.postbook.R
import de.olegrom.postbook.presentation.navigation.main.MainGraph
import de.olegrom.postbook.presentation.navigation.main.Screen
import de.olegrom.postbook.presentation.ui.main.TopAppBarViewModel
import de.olegrom.postbook.presentation.ui.posts.PostsViewModel
import de.olegrom.postbook.presentation.utils.TestTag
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberAnimatedNavController(),
    topAppBarViewModel: TopAppBarViewModel = getViewModel(),
    postsViewModel: PostsViewModel = getViewModel()
) {
    var canPop by remember { mutableStateOf(false) }
    var showFav by remember { mutableStateOf(false) }
    val title by topAppBarViewModel.title.collectAsState()
    val currentFavFilter = remember { mutableStateOf(false) }
    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
            controller.currentBackStackEntry?.destination?.route?.let {
                canPop = (it == Screen.Post.route)
                showFav = (it == Screen.Posts.route)
            }
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            title,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag(TestTag.appBarTitle),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    navigationIcon = {
                        if (canPop) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    actions = {
                        if (showFav) {
                            Box(Modifier.padding(end = 15.dp)) {
                                Icon(
                                    painter = painterResource(
                                        if (currentFavFilter.value) {
                                            R.drawable.ic_fav_active
                                        } else {
                                            R.drawable.ic_fav
                                        }
                                    ),
                                    modifier = Modifier.clickable {
                                        currentFavFilter.value = !currentFavFilter.value
                                        postsViewModel.showOnlyFavouritePosts.postValue(
                                            currentFavFilter.value
                                        )
                                        postsViewModel.getAllPosts(currentFavFilter.value)
                                    },
                                    contentDescription = "Filter by favourites",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                )
            },
            content = { padding ->
                val modifier = Modifier
                    .padding(padding)
                    .padding(start = 10.dp, end = 10.dp)
                    .padding(top = 0.dp)
                MainGraph(
                    navController = navController,
                    modifier = modifier,
                )
            },
        )
    }
}
