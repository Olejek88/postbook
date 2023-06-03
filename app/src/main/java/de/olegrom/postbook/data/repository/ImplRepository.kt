package de.olegrom.postbook.data.repository

import de.olegrom.postbook.data.remote.service.AbstractKtorService
import de.olegrom.postbook.data.repository.AbstractRepository

class ImplRepository(
    private val ktorService: AbstractKtorService
) : AbstractRepository() {
    override suspend fun getUser(id: Int) = ktorService.getUser(id)
    //override suspend fun getAllPosts(page: Int) = ktorService.getPosts(page)
    //override suspend fun getPost(id: String) = ktorService.getPost(id)
}




