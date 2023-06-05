package de.olegrom.postbook

import de.olegrom.postbook.data.remote.service.FakeKtorService
import de.olegrom.postbook.data.repository.ImplRepository
import de.olegrom.postbook.domain.domain_model.CommentDomainModel
import de.olegrom.postbook.domain.domain_model.PostDomainModel
import de.olegrom.postbook.domain.domain_model.UserDomainModel
import de.olegrom.postbook.domain.usecase.GetCommentsUseCase
import de.olegrom.postbook.domain.usecase.GetPostUseCase
import de.olegrom.postbook.domain.usecase.GetPostsUseCase
import de.olegrom.postbook.domain.usecase.GetUserUseCase
import de.olegrom.postbook.presentation.ui.login.LoginViewModel
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import de.olegrom.postbook.presentation.ui.main.ScreenState
import de.olegrom.postbook.presentation.ui.posts.PostsViewModel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

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
            viewModel.getPostsByUserId(1)
            val state : FlowCollector<ScreenState> = FlowCollector {
                println(it)
                if (it is ScreenState.Success) {
                    val entity = (it).entity
                    // TODO check
                    val firstPost = (entity as List<PostDomainModel>)[0]
                    assertTrue(firstPost.id == 1)
                    assertTrue(firstPost.userId == 1)
                    cancel()
                }
                if (it is ScreenState.Error) {
                    assertTrue(false)
                    cancel()
                }
            }
            viewModel.postsState.collect(state)
        }
    }

    @Test
    fun getPostTest() = runTest {
        setUp()
        launch {
            viewModel.getPostById(1)
            val state : FlowCollector<ScreenState> = FlowCollector {
                if (it is ScreenState.Success) {
                    println(it)
                    val entity = (it).entity
                    val firstPost = (entity as PostDomainModel)
                    assertTrue(firstPost.id == 1)
                    assertTrue(firstPost.userId == 1)
                    cancel()
                }
                if (it is ScreenState.Error) {
                    assertTrue(false)
                    cancel()
                }
            }
            viewModel.postsState.collect(state)
        }
    }

    @Test
    fun getCommentsTest() = runTest {
        setUp()
        launch {
            viewModel.getCommentsByPostId(1)
            val state : FlowCollector<ScreenState> = FlowCollector {
                if (it is ScreenState.Success) {
                    val entity = (it).entity
                    val comments = (entity as List<CommentDomainModel>)
                    assertTrue(comments.isNotEmpty())
                    assertTrue(comments[0].id == 1)
                    assertTrue(comments[0].postId == 1)
                    cancel()
                }
                if (it is ScreenState.Error) {
                    assertTrue(false)
                    cancel()
                }
            }
            viewModel.commentsState.collect(state)
        }
    }
}