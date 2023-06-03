package de.olegrom.postbook.data.remote.service

import de.olegrom.postbook.data.remote.dto.UserDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ImplKtorService(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : AbstractKtorService() {
    override suspend fun getUser(id: Int): UserDTO = httpClient.get("$baseUrl/${EndPoints.USERS}/${id}") {}.body()
    //override suspend fun getPosts(page: Int): StarshipsDTO = httpClient.get("$baseUrl/${EndPoints.STARSHIPS}/?page=${page}") {}.body()
    //override suspend fun getPost(id: String): FilmDTO = httpClient.get("$baseUrl/${EndPoints.FILMS}/${id}") {}.body()
}
