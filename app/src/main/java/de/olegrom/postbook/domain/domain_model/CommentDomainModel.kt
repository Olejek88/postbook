package de.olegrom.postbook.domain.domain_model

data class CommentDomainModel(
    var id: Int = 1,
    var postId: Int = 1,
    var name: String = "",
    var body: String = "",
    var email: String = ""
)
