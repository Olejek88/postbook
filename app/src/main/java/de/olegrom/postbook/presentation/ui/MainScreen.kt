package de.olegrom.postbook.presentation.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import de.olegrom.postbook.presentation.navigation.main.MainGraph
import de.olegrom.postbook.presentation.navigation.main.Screen
import de.olegrom.postbook.presentation.ui.main.TopAppBarViewModel
import de.olegrom.postbook.presentation.utils.TestTag
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(navController: NavHostController = rememberAnimatedNavController(),
               topAppBarViewModel: TopAppBarViewModel = getViewModel()) {
    var canPop by remember { mutableStateOf(false) }
    val title by topAppBarViewModel.title.collectAsState()
    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
            canPop = false
            controller.currentBackStackEntry?.destination?.route?.let {
                canPop = (it == Screen.Post.route)
            }
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().testTag(TestTag.appBarTitle),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    navigationIcon = {
                        if (canPop) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    actions = {
                        if (canPop) {
                            Box(modifier = Modifier.size(24.dp))
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                )
            },
            content = { padding ->
                val modifier = Modifier
                    .padding(padding)
                    .padding(start = 10.dp, end = 10.dp)
                    .padding(top = 0.dp)
                MainGraph(
                    navController = navController,
                    modifier = modifier,
                )
            },
        )
    }
}
