package de.olegrom.postbook.presentation.navigation.main

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Posts : Screen("posts")
    object Post : Screen("post/{postId}")
}
