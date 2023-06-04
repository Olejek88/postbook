package de.olegrom.postbook.data.remote.dto

import de.olegrom.postbook.domain.domain_model.PostDomainModel

@kotlinx.serialization.Serializable
data class PostDTO(
    var id: Int = 1,
    var userId: Int = 1,
    var title: String = "",
    var body: String = ""
)

fun List<PostDTO>.asDomainModels(): List<PostDomainModel> {
    return this.map {
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
