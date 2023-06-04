package de.olegrom.postbook.domain.domain_model

import android.os.Parcelable
import de.olegrom.postbook.data.local.dao.CommentDAO
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentDomainModel(
    var id: Int = 1,
    var postId: Int = 1,
    var name: String = "",
    var body: String = "",
    var email: String = ""
) : Parcelable

fun CommentDomainModel.asDao(): CommentDAO {
    return CommentDAO().also {
        it.id = this.id
        it.name = this.name
        it.postId = this.postId
        it.body = this.body
        it.email = this.email
    }
}

fun List<CommentDomainModel>.asDao() = map {
    it.asDao()
}
