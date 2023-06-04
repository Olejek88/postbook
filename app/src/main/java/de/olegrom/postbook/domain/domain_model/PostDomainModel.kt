package de.olegrom.postbook.domain.domain_model

import android.os.Parcelable
import de.olegrom.postbook.data.local.dao.PostDAO
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostDomainModel(
    var id: Int = 1,
    var userId: Int = 1,
    var title: String = "",
    var body: String = ""
) : Parcelable

fun PostDomainModel.asDao(): PostDAO {
    return PostDAO().also {
        it.id = this.id
        it.userId = this.userId
        it.title = this.title
        it.body = this.body
    }
}

fun List<PostDomainModel>.asDao() = map {
    it.asDao()
}
