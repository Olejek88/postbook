package de.olegrom.postbook.data.repository

import de.olegrom.postbook.data.remote.dto.UserDTO

abstract class AbstractRepository {
    abstract suspend fun getUser(id: Int): UserDTO?
    //abstract suspend fun getAllPosts(page: Int): PostsDTO
    //abstract suspend fun getPost(id: Int): PostDTO
}