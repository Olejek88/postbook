package de.olegrom.postbook.data.repository

import de.olegrom.postbook.data.remote.service.AbstractKtorService

class ImplRepository(
    private val ktorService: AbstractKtorService
) : AbstractRepository() {
    override suspend fun getUser(id: Int) = ktorService.getUser(id)
    override suspend fun getPosts() = ktorService.getPosts()
    override suspend fun getComments(postId: Int) = ktorService.getComments(postId)
    override suspend fun getPost(id: Int) = ktorService.getPost(id)
}




