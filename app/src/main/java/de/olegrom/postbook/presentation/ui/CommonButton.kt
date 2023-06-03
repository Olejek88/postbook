package de.olegrom.postbook.presentation.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    enabled: Boolean? = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        enabled = enabled ?: true,
        onClick = { onClick() },
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text(text = text, fontSize = 20.sp, color = Color.Black)
    }
}


@Preview
@Composable
fun ComposablePreview() {
    CommonButton(text = "Text", enabled = true) {}
}
