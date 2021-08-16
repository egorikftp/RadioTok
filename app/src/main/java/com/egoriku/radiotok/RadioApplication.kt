@file:Suppress("unused")

package com.egoriku.radiotok

import android.app.Application
import com.egoriku.radiotok.db.koin.dbModule
import com.egoriku.radiotok.koin.appScope
import com.egoriku.radiotok.koin.feedScreenModule
import com.egoriku.radiotok.koin.radioModule
import com.egoriku.radiotok.koin.settingsScreenModule
import com.egoriku.radiotok.radioplayer.koin.exoPlayerModule
import com.egoriku.radiotok.radioplayer.koin.radioPlayerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RadioApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RadioApplication)
            modules(
                appScope,
                dbModule,
                exoPlayerModule,
                feedScreenModule,
                radioModule,
                radioPlayerModule,
                settingsScreenModule
            )
        }
    }
}