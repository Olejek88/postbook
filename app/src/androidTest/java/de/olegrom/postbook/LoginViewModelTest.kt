package de.olegrom.postbook

import de.olegrom.postbook.data.remote.service.FakeKtorService
import de.olegrom.postbook.data.repository.ImplRepository
import de.olegrom.postbook.domain.usecase.GetUserUseCase
import de.olegrom.postbook.presentation.ui.login.LoginViewModel
import org.junit.Before
import org.junit.Test
import de.olegrom.postbook.presentation.ui.main.states.LoginState
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

class LoginViewModelTest {
    private val viewModel = LoginViewModel(GetUserUseCase(ImplRepository(FakeKtorService())))

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun loginUserTest() = runTest {
        setUp()
        launch {
            viewModel.getUserById(1)
            val state : FlowCollector<LoginState> = FlowCollector {
                println(it)
                if (it is LoginState.Success) {
                    val entity = (it).user
                    assertTrue(entity.id == 1)
                    assertTrue((entity).name == "Leanne Graham")
                    cancel()
                }
                if (it is LoginState.Error) {
                    assertTrue(false)
                    cancel()
                }
            }
            viewModel.state.collect(state)
        }
    }
}