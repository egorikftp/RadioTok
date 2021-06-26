package com.egoriku.radiotok.db.koin

import androidx.room.Room
import com.egoriku.radiotok.db.RadioTokDb
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(), RadioTokDb::class.java, "radiotok"
        ).fallbackToDestructiveMigration()
            .build()
    }
}