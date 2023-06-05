package de.olegrom.postbook.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferenceHelper (context: Context) {
    companion object {
        private const val PREF_FAVOURITES = "PREF_FAVOURITES"
    }

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences("postbook", Context.MODE_PRIVATE)

    fun getFavourites(): MutableSet<String>? {
        return mPrefs.getStringSet(PREF_FAVOURITES, mutableSetOf())
    }

    fun setFavourites(favouritesSet: MutableSet<String>) {
        mPrefs.edit { putStringSet(PREF_FAVOURITES, favouritesSet) }
    }
}
