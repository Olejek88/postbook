package de.olegrom.postbook.presentation.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import de.olegrom.postbook.R

@Composable
fun SimpleAlertDialog(title: String, subtitle: String,
                      showDismissButton: Boolean? = true,
                      openDialogState: MutableState<Boolean>,
                      onConfirmClick: () -> Unit) {
    if (openDialogState.value) {
        AlertDialog(
            onDismissRequest = { openDialogState.value = false },
            confirmButton = {
                TextButton(onClick = { onConfirmClick() }) { Text(stringResource(R.string.OK)) }
            },
            dismissButton = {
                if (showDismissButton == true) {
                    TextButton(onClick = {
                        openDialogState.value = false
                    }) { Text(stringResource(R.string.cancel)) }
                }
            },
            title = { Text(text = title) },
            text = { Text(text = subtitle) }
        )
    }
}
