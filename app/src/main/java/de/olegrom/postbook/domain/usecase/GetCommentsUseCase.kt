package de.olegrom.postbook.domain.usecase

import de.olegrom.postbook.data.remote.dto.asDomainModels
import de.olegrom.postbook.data.repository.AbstractRepository
import kotlinx.coroutines.flow.flow

class GetCommentsUseCase(
    private val repository: AbstractRepository
) {
    operator fun invoke(id: Int) = flow {
        val response = repository.getComments(id).asDomainModels()
        emit(response)
    }
}