package de.olegrom.postbook.domain.usecase

import de.olegrom.postbook.data.remote.dto.asDomainModels
import de.olegrom.postbook.data.repository.AbstractRepository
import kotlinx.coroutines.flow.flow

class GetPostsUseCase(
    private val repository: AbstractRepository
) {
    operator fun invoke(userId: Int) = flow {
        val response = repository.getPosts().asDomainModels().filter { it.userId == userId }
        emit(response)
    }
}