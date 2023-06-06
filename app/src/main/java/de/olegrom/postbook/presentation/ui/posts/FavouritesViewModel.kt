package de.olegrom.postbook.presentation.ui.posts

import androidx.lifecycle.ViewModel
import de.olegrom.postbook.data.preferences.SharedPreferenceHelper

class FavouritesViewModel(private val preferences: SharedPreferenceHelper) : ViewModel() {

    fun isPostFavourite(postId: Int) = gerFavourites().contains(postId.toString())

    private fun gerFavourites(): MutableSet<String> = preferences.getFavourites() ?: mutableSetOf()

    fun togglePostFavourite(postId: Int) {
        if (!isPostFavourite(postId)) {
            preferences.addFavourites(postId.toString())
        } else {
            preferences.removeFavourites(postId.toString())
        }
    }
}
