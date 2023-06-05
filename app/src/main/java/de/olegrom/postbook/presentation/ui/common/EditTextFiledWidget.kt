package de.olegrom.postbook.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import de.olegrom.postbook.presentation.utils.TestTag

@Composable
fun EditTextFieldWidget(
    title: String,
    supportingText: String? = null,
    hasError: Boolean = false,
    onTextChanged: (text: String) -> Unit,
    type: KeyboardType,
    isLastField: Boolean? = false,
    initialValue: String = ""
) {
    var text by remember { mutableStateOf(initialValue) }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.testTag(TestTag.inputField).fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            value = text,
            label = { Text(text = title) },
            supportingText = { if (supportingText!=null) Text(text = supportingText, style = MaterialTheme.typography.bodySmall) },
            onValueChange = {
                onTextChanged(it)
                text = it
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = type,
                imeAction = if(isLastField==true) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            singleLine = true,
            isError = hasError,
        )
    }
}
