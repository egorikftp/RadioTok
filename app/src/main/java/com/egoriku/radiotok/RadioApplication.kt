@file:Suppress("unused")

package com.egoriku.radiotok

import android.app.Application
import com.egoriku.radiotok.datasource.koin.dataSourceModule
import com.egoriku.radiotok.db.koin.dbModule
import com.egoriku.radiotok.koin.*
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
                dataSourceModule,
                dbModule,
                exoPlayerModule,
                feedScreenModule,
                network,
                playlistScreenModule,
                radioModule,
                radioPlayerModule,
                settingsScreenModule
            )
        }
    }
}