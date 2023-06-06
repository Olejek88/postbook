package de.olegrom.postbook.presentation.ui.posts.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.olegrom.postbook.domain.domain_model.CommentDomainModel
import de.olegrom.postbook.presentation.utils.TestTag

@Composable
fun CommentCard(
    comment: CommentDomainModel,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .testTag(TestTag.commentCard),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = comment.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = comment.body,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Email: ${comment.email}",
                    style = MaterialTheme.typography.bodySmall
                        .copy(fontStyle = FontStyle.Italic, color = MaterialTheme.colorScheme.error),
                )
            }
        }
    }
}

@Preview
@Composable
fun CommentCardPreview() {
    CommentCard(CommentDomainModel(name="Name", body = "Text", email = "oleg.romanov@mmm.de"))
}