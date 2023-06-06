package de.olegrom.postbook.domain.usecase

import de.olegrom.postbook.data.preferences.SharedPreferenceHelper
import de.olegrom.postbook.data.remote.dto.asDomainModels
import de.olegrom.postbook.data.repository.AbstractRepository
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GetPostsUseCase(
    private val repository: AbstractRepository,
    private val preferences: SharedPreferenceHelper? = null
) {
    operator fun invoke(userId: Int, onlyFav: Boolean) = flow {
        val favourites = preferences?.getFavourites() ?: mutableSetOf()
        val response = repository.getPosts().asDomainModels().filter {
            (it.userId == userId && (!onlyFav || favourites.contains(it.id.toString())))
        }
        emit(response)
    }
}