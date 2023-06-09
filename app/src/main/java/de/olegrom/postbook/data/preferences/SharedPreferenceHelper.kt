package de.olegrom.postbook.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferenceHelper (context: Context) {
    companion object {
        private const val PREF_FAVOURITES = "PREF_FAVOURITES"
        private const val USER_ID = "USER_ID"
    }

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences("postbook", Context.MODE_PRIVATE)

    fun getFavourites(): MutableSet<String>? {
        return mPrefs.getStringSet(PREF_FAVOURITES, mutableSetOf())
    }

    fun addFavourites(postId: String) {
        val setFromPrefs = mPrefs.getStringSet(PREF_FAVOURITES, mutableSetOf()) ?: mutableSetOf()
        val copyOfSet = setFromPrefs.toMutableSet()
        copyOfSet.add(postId)
        val editor = mPrefs.edit()
        editor.putStringSet(PREF_FAVOURITES, copyOfSet)
        editor.apply()
    }

    fun removeFavourites(postId: String) {
        val setFromPrefs = mPrefs.getStringSet(PREF_FAVOURITES, mutableSetOf()) ?: mutableSetOf()
        val copyOfSet = setFromPrefs.toMutableSet()
        copyOfSet.remove(postId)
        val editor = mPrefs.edit()
        editor.putStringSet(PREF_FAVOURITES, copyOfSet)
        editor.apply()
    }

    fun getUserId(): Int {
        return mPrefs.getInt(USER_ID, 1)
    }

    fun setUserId(id: Int) {
        mPrefs.edit { putInt(USER_ID, id) }
    }
}
