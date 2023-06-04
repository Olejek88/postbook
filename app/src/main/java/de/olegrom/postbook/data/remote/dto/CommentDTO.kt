package de.olegrom.postbook.data.remote.dto

import de.olegrom.postbook.domain.domain_model.CommentDomainModel

@kotlinx.serialization.Serializable
data class CommentDTO(
    var id: Int = 1,
    var postId: Int = 1,
    var name: String = "",
    var body: String = "",
    var email: String = ""
)

fun List<CommentDTO>.asDomainModels(): List<CommentDomainModel> {
    return this.map {
        it.asDomainModel()
    }
}

fun CommentDTO.asDomainModel(): CommentDomainModel {
    return CommentDomainModel(
            id = this.id,
            postId = this.postId,
            name = this.name,
            email = this.email,
            body = this.body
        )
}
