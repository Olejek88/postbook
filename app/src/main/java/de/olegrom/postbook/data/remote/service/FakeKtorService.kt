package de.olegrom.postbook.data.remote.service

import de.olegrom.postbook.data.remote.dto.CommentDTO
import de.olegrom.postbook.data.remote.dto.PostDTO
import de.olegrom.postbook.data.remote.dto.UserDTO

class FakeKtorService : AbstractKtorService() {
    val mockedPost = PostDTO(1, 1, "Post title", "Post text")
    val mockedPost2 = PostDTO(2, 1, "Post title 2", "Post text 2")
    val mockedComment = CommentDTO(1, 1, "Oleg", "Comment text")
    val mockedUser = UserDTO(1, "Leanne Graham", "Bret", "Sincere@april.biz")

    override suspend fun getUser(id: Int): UserDTO {
        return mockedUser
    }
    override suspend fun getPosts(): List<PostDTO> {
        return listOf(mockedPost, mockedPost2)
    }
    override suspend fun getComments(postId: Int): List<CommentDTO> {
        return listOf(mockedComment)
    }
    override suspend fun getPost(id: Int): PostDTO {
        return mockedPost
    }
}

