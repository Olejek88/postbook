package de.olegrom.postbook

import de.olegrom.postbook.data.remote.service.FakeKtorService
import de.olegrom.postbook.data.repository.ImplRepository
import de.olegrom.postbook.domain.domain_model.UserDomainModel
import de.olegrom.postbook.domain.usecase.GetUserUseCase
import de.olegrom.postbook.presentation.ui.login.LoginViewModel
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import de.olegrom.postbook.presentation.ui.main.ScreenState
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
            val state : FlowCollector<ScreenState> = FlowCollector {
                println(it)
                if (it is ScreenState.Success) {
                    val entity = (it).entity
                    assertTrue(entity is UserDomainModel)
                    assertTrue((entity as UserDomainModel).id == 1)
                    assertTrue((entity).name == "Leanne Graham")
                    cancel()
                }
                if (it is ScreenState.Error) {
                    assertTrue(false)
                    cancel()
                }
            }
            viewModel.state.collect(state)
        }
    }
}