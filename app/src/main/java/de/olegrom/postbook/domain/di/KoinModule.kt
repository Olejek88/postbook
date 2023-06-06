package de.olegrom.postbook.domain.di

import de.olegrom.postbook.data.preferences.SharedPreferenceHelper
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
import de.olegrom.postbook.presentation.ui.posts.FavouritesViewModel
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
import org.koin.android.ext.koin.androidContext

fun initKoin(
    enableNetworkLogs: Boolean = false,
    baseUrl: String,
    appDeclaration: KoinAppDeclaration = {}
) =
    startKoin {
        appDeclaration()
        modules(commonModule(baseUrl))
    }

fun commonModule(baseUrl: String) =
    getUseCaseModule() + getDateModule(
        baseUrl
    ) + getHelperModule() + getViewModelsModule()

fun getHelperModule() = module {

}

fun getDateModule(baseUrl: String) = module {
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

    single { SharedPreferenceHelper(androidContext()) }

    single {
        createHttpClient(
            get(),
            get(),
        )
    }


}

fun getUseCaseModule() = module {
    single {
        GetUserUseCase(get())
    }
    single {
        GetPostsUseCase(get(), get())
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
        LoginViewModel(get(), get())
    }
    single {
        PostsViewModel(get(), get(), get(), get())
    }
    single {
        FavouritesViewModel(get())
    }
}

fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json
) =
    HttpClient(httpClientEngine) {

        install(HttpCache)
        install(ContentNegotiation) {
            json(json)
        }
    }

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

