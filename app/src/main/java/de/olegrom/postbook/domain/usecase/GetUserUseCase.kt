package de.olegrom.postbook.domain.usecase

import de.olegrom.postbook.data.remote.dto.asDomainModel
import de.olegrom.postbook.data.repository.AbstractRepository
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GetUserUseCase(
    private val repository: AbstractRepository
) {
    operator fun invoke(id: Int) = flow {
        val response = repository.getUser(id)?.asDomainModel()
        Timber.i("user = ${response}")
        emit(response)
    }
}