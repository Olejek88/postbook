package de.olegrom.postbook.domain.domain_model

data class PostDomainModel(
    var id: Int = 1,
    var userId: Int = 1,
    var title: String = "",
    var body: String = ""
)
