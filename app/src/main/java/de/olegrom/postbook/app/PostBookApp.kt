package de.olegrom.postbook.app

import android.app.Application
import de.olegrom.postbook.BuildConfig
import de.olegrom.postbook.domain.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class PostBookApp : Application() {
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }

    override fun onCreate() {
        super.onCreate()
        initKoin(baseUrl = BASE_URL, enableNetworkLogs = BuildConfig.DEBUG) {
            androidContext(this@PostBookApp)
            modules(
                listOf(module {
                })
            )
        }
    }
}