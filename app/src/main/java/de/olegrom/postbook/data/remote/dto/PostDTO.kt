package de.olegrom.postbook.data.remote.dto

import de.olegrom.postbook.domain.domain_model.PostDomainModel

@kotlinx.serialization.Serializable
data class PostsDTO(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PostDTO>,
)

@kotlinx.serialization.Serializable
data class PostDTO(
    var id: Int = 1,
    var userId: Int = 1,
    var title: String = "",
    var body: String = ""
)

fun PostsDTO.asDomainModel(): List<PostDomainModel> {
    return this.results.map {
        it.asDomainModel()
    }
}

fun PostDTO.asDomainModel(): PostDomainModel {
    return PostDomainModel(
            id = this.id,
            userId = this.userId,
            title = this.title,
            body = this.body
        )
}
