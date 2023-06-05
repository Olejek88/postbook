package de.olegrom.postbook.ui

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import de.olegrom.postbook.MainActivity
import de.olegrom.postbook.presentation.navigation.root.RootNavigationGraph
import de.olegrom.postbook.presentation.theme.PostBookTheme
import de.olegrom.postbook.presentation.utils.TestTag
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ScreensFlowTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        runBlocking {
        }
        setContent()
    }

    private fun setContent() {
        composeRule.activity.setContent {
            PostBookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNavigationGraph(navHostController = rememberNavController())
                }
            }
        }
    }

    @Test
    fun testMainScreen() {
        composeRule.onNodeWithTag(TestTag.appBarTitle).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTag.inputField).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTag.inputField).performTextInput("1")
        composeRule.onNodeWithTag(TestTag.loginButton).performClick()
        composeRule.waitUntil(5000) {
            composeRule.onAllNodesWithTag(TestTag.postCard).fetchSemanticsNodes().isNotEmpty()
        }
        // or on click with specific tag post
        composeRule.onNodeWithText("qui est esse").performClick()
        composeRule.waitUntil(5000) {
            composeRule.onAllNodesWithTag(TestTag.commentCard).fetchSemanticsNodes().isNotEmpty()
            composeRule.onAllNodesWithTag(TestTag.postCard).fetchSemanticsNodes().isNotEmpty()
        }
    }
}