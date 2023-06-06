package de.olegrom.postbook.presentation.ui.posts.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.olegrom.postbook.presentation.utils.TestTag
import de.olegrom.postbook.R
import de.olegrom.postbook.domain.domain_model.CommentDomainModel
import de.olegrom.postbook.domain.domain_model.PostDomainModel
import de.olegrom.postbook.presentation.ui.posts.FavouritesViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PostCard(
    post: PostDomainModel,
    maxTextLines: Int = 3,
    favouritesViewModel: FavouritesViewModel = getViewModel(),
    itemClick: () -> Unit = {}
) {
    val currentFavStatus = mutableStateOf(favouritesViewModel.isPostFavourite(post.id))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { itemClick() }
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .testTag(TestTag.postCard),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(5F),
                        text = post.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        painter = painterResource(if (currentFavStatus.value) R.drawable.ic_fav_active else R.drawable.ic_fav),
                        modifier = Modifier.clickable {
                            favouritesViewModel.togglePostFavourite(post.id)
                            currentFavStatus.value = !currentFavStatus.value
                        },
                        contentDescription = post.title,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = maxTextLines,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}