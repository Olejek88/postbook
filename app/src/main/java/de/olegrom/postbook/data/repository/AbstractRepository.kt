package de.olegrom.postbook.data.repository

import de.olegrom.postbook.data.remote.dto.CommentDTO
import de.olegrom.postbook.data.remote.dto.PostDTO
import de.olegrom.postbook.data.remote.dto.UserDTO

abstract class AbstractRepository {
    abstract suspend fun getUser(id: Int): UserDTO?
    abstract suspend fun getPosts(userId: Int): List<PostDTO>
    abstract suspend fun getComments(postId: Int): List<CommentDTO>
    abstract suspend fun getPost(id: Int): PostDTO
}