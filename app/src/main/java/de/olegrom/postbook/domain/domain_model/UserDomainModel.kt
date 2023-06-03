package de.olegrom.postbook.domain.domain_model

import android.os.Parcelable
import de.olegrom.postbook.data.local.dao.UserDAO
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDomainModel(
    var id: Int = 1,
    var name: String = "",
    var username: String = "",
    var email: String = ""
) : Parcelable

fun UserDomainModel.asDao(): UserDAO {
    return UserDAO().also {
        it.id = this.id
        it.name = this.name
        it.username = this.username
        it.email = this.email
    }
}

fun List<UserDomainModel>.asDao() = map {
    it.asDao()
}
