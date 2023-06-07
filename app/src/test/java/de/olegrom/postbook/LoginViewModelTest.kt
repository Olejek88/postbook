package de.olegrom.postbook

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.olegrom.postbook.data.remote.dto.asDomainModel
import de.olegrom.postbook.data.remote.service.FakeKtorService
import de.olegrom.postbook.data.repository.ImplRepository
import de.olegrom.postbook.domain.usecase.GetUserUseCase
import de.olegrom.postbook.presentation.ui.login.LoginViewModel
import de.olegrom.postbook.presentation.ui.main.states.LoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class LoginViewModelTest {
    private val fakeKtorService = FakeKtorService()
    private val viewModel = LoginViewModel(
        GetUserUseCase(ImplRepository(fakeKtorService))
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given userID = 1, should return mocked user for this userId`() = runTest {
        val loginStateFlow = viewModel.state
        val loginState = mutableListOf<LoginState>()
        val job = launch {
            loginStateFlow.toList(loginState)
        }
        viewModel.getUserById(1)
        runCurrent()
        assert(loginState.last() == LoginState.Success(fakeKtorService.mockedUser.asDomainModel()!!))
        job.cancel()
    }
}