package de.olegrom.postbook.data.remote.service

import de.olegrom.postbook.data.remote.dto.CommentDTO
import de.olegrom.postbook.data.remote.dto.PostDTO
import de.olegrom.postbook.data.remote.dto.UserDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ImplKtorService(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : AbstractKtorService() {
    override suspend fun getUser(id: Int): UserDTO = httpClient.get("$baseUrl/${EndPoints.USERS}/${id}") {}.body()
    override suspend fun getPosts(): List<PostDTO> = httpClient.get("$baseUrl/${EndPoints.POSTS}") {}.body()
    override suspend fun getComments(postId: Int): List<CommentDTO> = httpClient.get("$baseUrl/${EndPoints.COMMENTS}/${postId}") {}.body()
    override suspend fun getPost(id: Int): PostDTO = httpClient.get("$baseUrl/${EndPoints.POSTS}/${id}") {}.body()
}
