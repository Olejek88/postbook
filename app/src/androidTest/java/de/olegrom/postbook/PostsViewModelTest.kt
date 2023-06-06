package de.olegrom.postbook

import de.olegrom.postbook.data.remote.service.FakeKtorService
import de.olegrom.postbook.data.repository.ImplRepository
import de.olegrom.postbook.domain.usecase.GetCommentsUseCase
import de.olegrom.postbook.domain.usecase.GetPostUseCase
import de.olegrom.postbook.domain.usecase.GetPostsUseCase
import de.olegrom.postbook.presentation.ui.main.states.CommentsState
import org.junit.Before
import org.junit.Test
import de.olegrom.postbook.presentation.ui.main.states.PostState
import de.olegrom.postbook.presentation.ui.main.states.PostsState
import de.olegrom.postbook.presentation.ui.posts.PostsViewModel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

class PostsViewModelTest {
    private val viewModel = PostsViewModel(
        GetPostUseCase(ImplRepository(FakeKtorService())),
        GetPostsUseCase(ImplRepository(FakeKtorService())),
        GetCommentsUseCase(ImplRepository(FakeKtorService()))
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun allPostsTest() = runTest {
        setUp()
        launch {
            viewModel.getAllPosts(false)
            assertTrue(viewModel.postsState.value is PostsState.Success)
        }
    }

    @Test
    fun getPostTest() = runTest {
        setUp()
        launch {
            viewModel.getPostById(1)
            val state : FlowCollector<PostState> = FlowCollector {
                if (it is PostState.Success) {
                    println(it)
                    val post = (it).post
                    assertTrue(post.id == 1)
                    assertTrue(post.userId == 1)
                    cancel()
                }
                if (it is PostState.Error) {
                    assertTrue(false)
                    cancel()
                }
            }
            viewModel.postDetailState.collect(state)
        }
    }

    @Test
    fun getCommentsTest() = runTest {
        setUp()
        launch {
            viewModel.getCommentsByPostId(1)
            val state : FlowCollector<CommentsState> = FlowCollector {
                if (it is CommentsState.Success) {
                    val comments = (it).comments
                    assertTrue(comments.isNotEmpty())
                    assertTrue(comments[0].id == 1)
                    assertTrue(comments[0].postId == 1)
                    cancel()
                }
                if (it is CommentsState.Error) {
                    assertTrue(false)
                    cancel()
                }
            }
            viewModel.commentsState.collect(state)
        }
    }
}