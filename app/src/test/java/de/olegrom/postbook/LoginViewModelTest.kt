package de.olegrom.postbook

import de.olegrom.postbook.data.preferences.SharedPreferenceHelper
import de.olegrom.postbook.domain.domain_model.UserDomainModel
import de.olegrom.postbook.domain.usecase.GetUserUseCase
import de.olegrom.postbook.presentation.ui.login.LoginViewModel
import de.olegrom.postbook.presentation.ui.main.states.LoginState
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Before

internal class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel
    private val getUserUseCase: GetUserUseCase = mockk()
    private val preferenceHelper: SharedPreferenceHelper = mockk()
    private lateinit var viewStates: MutableList<LoginState.Idle>

    @Before
    fun setUp() {
        //Dispatchers.setMain(Dispatchers.Unconfined)
        viewStates = mutableListOf()
        viewModel = LoginViewModel(getUserUseCase, preferenceHelper)
    }

    @Before
    fun getUserById() {
        viewModel.getUserById(1)
        coVerify {
            getUserUseCase.invoke(1)
        }
        Assert.assertEquals(LoginState.Loading, viewStates[0])
        Assert.assertEquals(LoginState.Success(UserDomainModel()), viewStates[1])
    }
}