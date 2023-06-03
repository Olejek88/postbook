package de.olegrom.postbook.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import de.olegrom.postbook.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextFieldWidget(
    title: String,
    supportingText: String? = null,
    hasError: Boolean = false,
    isPasswordField: Boolean? = false,
    onTextChanged: (text: String) -> Unit,
    type: KeyboardType,
    isLastField: Boolean? = false,
    initialValue: String = ""
) {
    var text by remember { mutableStateOf(initialValue) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
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
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            ),
            visualTransformation = if (passwordVisible || isPasswordField == false)
                VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (isPasswordField == true) {
                    val image = if (passwordVisible)
                        painterResource(R.drawable.ic_visibility_black_24dp)
                    else painterResource(R.drawable.ic_visibility_off_black_24dp)
                    val description = if (passwordVisible) stringResource(R.string.hidePassword) else stringResource(R.string.showPassword)
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(image, description)
                    }
                }
            }
        )
    }
}
