package de.olegrom.postbook.presentation.ui.posts

import androidx.lifecycle.ViewModel
import de.olegrom.postbook.data.preferences.SharedPreferenceHelper
import timber.log.Timber

class FavouritesViewModel(val preferences: SharedPreferenceHelper) : ViewModel() {

    fun isPostFavourite(postId: Int) = gerFavourites().contains(postId.toString())

    private fun gerFavourites(): MutableSet<String> = preferences.getFavourites() ?: mutableSetOf()

    fun togglePostFavourite(postId: Int) {
        val currentFavourites = gerFavourites()
        Timber.i("currentFavourites=${currentFavourites}")
        if (!isPostFavourite(postId)) {
            currentFavourites.add(postId.toString())
        } else {
            currentFavourites.remove(postId.toString())
        }
        Timber.i("currentFavourites=${currentFavourites}")
        preferences.setFavourites(currentFavourites)
    }
}
