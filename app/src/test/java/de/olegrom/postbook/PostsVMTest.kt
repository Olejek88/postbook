package de.olegrom.postbook

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import de.olegrom.postbook.data.remote.dto.asDomainModel
import de.olegrom.postbook.data.remote.service.FakeKtorService
import de.olegrom.postbook.data.repository.ImplRepository
import de.olegrom.postbook.domain.usecase.GetCommentsUseCase
import de.olegrom.postbook.domain.usecase.GetPostUseCase
import de.olegrom.postbook.domain.usecase.GetPostsUseCase
import de.olegrom.postbook.presentation.ui.main.states.CommentsState
import de.olegrom.postbook.presentation.ui.main.states.PostState
import de.olegrom.postbook.presentation.ui.main.states.PostsState
import de.olegrom.postbook.presentation.ui.posts.PostsViewModel
import io.mockk.*
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class PostsVMTest {
    private val fakeKtorService = FakeKtorService()
    private val viewModel = PostsViewModel(
        GetPostUseCase(ImplRepository(fakeKtorService)),
        GetPostsUseCase(ImplRepository(fakeKtorService)),
        GetCommentsUseCase(ImplRepository(fakeKtorService))
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `Should return mocked posts`() {
        viewModel.getAllPosts(false)
        val observer = mockk<Observer<PostsState>> { every { onChanged(any()) } just Runs }
        viewModel.postsState.observeForever(observer)
        verifySequence {
            observer.onChanged(
                PostsState.Success(
                    listOf(
                        fakeKtorService.mockedPost.asDomainModel(),
                        fakeKtorService.mockedPost2.asDomainModel()
                    )
                )
            )
        }
    }

    @Test
    fun `Given userID, should return mocked post for user`() {
        viewModel.getPostById(1)
        // TODO implement test
    }
}

// Reusable JUnit4 TestRule to override the Main dispatcher
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}