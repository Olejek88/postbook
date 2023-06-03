package de.olegrom.postbook.data.remote.service

import de.olegrom.postbook.data.remote.dto.UserDTO

abstract class AbstractKtorService {
    abstract suspend fun getUser(id: Int): UserDTO?
    //abstract suspend fun getPosts(page: Int): PostsDTO
    //abstract suspend fun getPost(id: String): PostDTO
}

