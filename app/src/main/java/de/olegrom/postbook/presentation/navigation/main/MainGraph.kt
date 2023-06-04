package de.olegrom.postbook.presentation.navigation.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import de.olegrom.postbook.presentation.ui.login.LoginScreen
import de.olegrom.postbook.presentation.ui.posts.PostScreen
import de.olegrom.postbook.presentation.ui.posts.PostsScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.composable(
    route: String,
    content: @Composable () -> Unit,
) {
    composable(
        route = route,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(800))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(800))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(800))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(800))
        }
    ) {
        content()
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    AnimatedNavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(modifier, navController)
        }
        composable(Screen.Posts.route) {
            PostsScreen(modifier, navController)
        }
        composable(Screen.Post.route) {
            val postId = it.arguments?.getString("postId") ?: "1"
            PostScreen(modifier, postId.toInt())
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val MAIN = "main_graph"
}