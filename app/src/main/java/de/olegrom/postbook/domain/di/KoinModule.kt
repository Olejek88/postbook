package de.olegrom.postbook.domain.di

import de.olegrom.postbook.data.remote.service.AbstractKtorService
import de.olegrom.postbook.data.remote.service.ImplKtorService
import de.olegrom.postbook.data.repository.AbstractRepository
import de.olegrom.postbook.data.repository.ImplRepository
import de.olegrom.postbook.domain.usecase.GetCommentsUseCase
import de.olegrom.postbook.domain.usecase.GetPostUseCase
import de.olegrom.postbook.domain.usecase.GetPostsUseCase
import de.olegrom.postbook.domain.usecase.GetUserUseCase
import de.olegrom.postbook.presentation.ui.login.LoginViewModel
import de.olegrom.postbook.presentation.ui.main.TopAppBarViewModel
import de.olegrom.postbook.presentation.ui.posts.PostsViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import io.ktor.client.engine.android.*

fun initKoin(
    enableNetworkLogs: Boolean = false,
    baseUrl: String,
    appDeclaration: KoinAppDeclaration = {}
) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs, baseUrl))
    }

fun commonModule(enableNetworkLogs: Boolean, baseUrl: String) =
    getUseCaseModule() + getDateModule(
        enableNetworkLogs,
        baseUrl
    ) + getHelperModule() + getViewModelsModule()

fun getHelperModule() = module {

}

fun getDateModule(enableNetworkLogs: Boolean, baseUrl: String) = module {
    single {
        Android.create()
    }

    single<AbstractRepository> {
        ImplRepository(
            get()
        )
    }

    single<AbstractKtorService> {
        ImplKtorService(
            get(),
            baseUrl
        )
    }

    single { createJson() }

    single {
        createHttpClient(
            get(),
            get(),
            enableNetworkLogs = enableNetworkLogs
        )
    }


}

fun getUseCaseModule() = module {
    single {
        GetUserUseCase(get())
    }
    single {
        GetPostsUseCase(get())
    }
    single {
        GetPostUseCase(get())
    }
    single {
        GetCommentsUseCase(get())
    }
}

fun getViewModelsModule() = module {
    single {
        TopAppBarViewModel()
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        PostsViewModel(get(), get(), get())
    }
}

fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    enableNetworkLogs: Boolean
) =
    HttpClient(httpClientEngine) {

        install(HttpCache)
        install(ContentNegotiation) {
            json(json)
        }
/*
        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
*/
    }

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

