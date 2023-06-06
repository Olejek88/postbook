package de.olegrom.postbook.presentation.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import de.olegrom.postbook.presentation.ui.common.EditTextFieldWidget
import kotlinx.coroutines.flow.update
import de.olegrom.postbook.presentation.ui.main.TopAppBarViewModel
import de.olegrom.postbook.presentation.ui.CommonButton
import org.koin.androidx.compose.getViewModel
import de.olegrom.postbook.R
import de.olegrom.postbook.domain.domain_model.UserDomainModel
import de.olegrom.postbook.presentation.navigation.main.Screen
import de.olegrom.postbook.presentation.ui.common.ErrorWidget
import de.olegrom.postbook.presentation.ui.common.PageLoadingView
import de.olegrom.postbook.presentation.ui.main.states.LoginState
import de.olegrom.postbook.presentation.ui.posts.PostsViewModel
import de.olegrom.postbook.presentation.utils.TestTag
import timber.log.Timber

@Composable
fun LoginScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: LoginViewModel = getViewModel(),
    postViewModel: PostsViewModel = getViewModel(),
    topAppBarViewModel: TopAppBarViewModel = getViewModel()
) {
    var currentUser by remember { mutableStateOf<UserDomainModel?>(null)}
    val state by viewModel.state.collectAsState()
    topAppBarViewModel.title.update { stringResource(id = R.string.login) }
    var id by remember { mutableStateOf("") }
    var isShowAlert by remember { mutableStateOf(false) }
    var alertText by remember { mutableStateOf("") }
    val wrongUserIdError = stringResource(id = R.string.wrong_user_id)
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            EditTextFieldWidget(
                title = stringResource(R.string.user_id),
                onTextChanged = { id = it },
                type = KeyboardType.Email,
            )
            if (isShowAlert) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = alertText,
                    color = MaterialTheme.colorScheme.error
                )
            }
            CommonButton(
                text = stringResource(R.string.login),
                enabled = id.isNotEmpty() && id.isDigitsOnly(),
                modifier = Modifier
                    .testTag(TestTag.loginButton)
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                onClick = {
                    try {
                        viewModel.getUserById(id.toInt())
                    } catch (e: Exception) {
                        isShowAlert = true
                        alertText = wrongUserIdError
                    }
                }
            )
            when (state) {
                is LoginState.Error -> ErrorWidget((state as LoginState.Error).errorMessage)
                is LoginState.Idle -> {}
                is LoginState.Loading -> {
                    PageLoadingView(modifier)
                }
                is LoginState.Success -> {
                    if (currentUser == null) {
                        currentUser = (state as LoginState.Success).user
                        postViewModel.getAllPosts(false)
                        // TODO store user data?
                    }
                    navController.navigate(Screen.Posts.route)
                }
            }
        }
    }
}
