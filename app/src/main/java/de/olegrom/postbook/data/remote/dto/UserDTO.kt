package de.olegrom.postbook.data.remote.dto

import de.olegrom.postbook.domain.domain_model.UserDomainModel

@kotlinx.serialization.Serializable
data class UserDTO(
    var id: Int = 1,
    var name: String = "",
    var username: String = "",
    var email: String = ""
)

fun UserDTO.asDomainModel(): UserDomainModel {
    return UserDomainModel(
        id = this.id,
        name = this.name,
        username = this.username,
        email = this.email,
    )
}
