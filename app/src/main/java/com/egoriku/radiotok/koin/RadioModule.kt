package com.egoriku.radiotok.koin

import com.egoriku.radiotok.data.Api
import com.egoriku.radiotok.data.datasource.RadioServerDataSource
import com.egoriku.radiotok.data.datasource.StationsDataSource
import com.egoriku.radiotok.domain.IRadioFetchUseCase
import com.egoriku.radiotok.domain.RadioFetchUseCase
import com.egoriku.radiotok.domain.datasource.IRadioServerDataSource
import com.egoriku.radiotok.domain.datasource.IStationsDataSource
import com.egoriku.radiotok.exoplayer.data.LikedRadioStationsHolder
import com.egoriku.radiotok.exoplayer.data.RadioStationsHolder
import com.egoriku.radiotok.presentation.IMusicServiceConnection
import com.egoriku.radiotok.presentation.MainActivity
import com.egoriku.radiotok.presentation.MusicServiceConnection
import com.egoriku.radiotok.presentation.RadioViewModel
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val radioModule = module {
    factory<IRadioServerDataSource> { RadioServerDataSource() }
    factory<IStationsDataSource> { StationsDataSource(api = get()) }

    single<IRadioFetchUseCase> {
        RadioFetchUseCase(
            radioServerDataSource = get(),
            stationsDataSource = get()
        )
    }

    single {
        LikedRadioStationsHolder()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<Api> {
        get<Retrofit>().create(Api::class.java)
    }

    single { RadioStationsHolder(radioFetchUseCase = get()) }

    single {
        AudioAttributes.Builder()
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()
    }

    single {
        DefaultHttpDataSource.Factory()
    }

    single {
        SimpleExoPlayer.Builder(androidContext())
            .setAudioAttributes(get(), true)
            .setHandleAudioBecomingNoisy(true)
            .setWakeMode(C.WAKE_MODE_NETWORK)
            .build()
    }

    single<IMusicServiceConnection> {
        MusicServiceConnection(context = androidContext())
    }

    scope<MainActivity> {
        viewModel {
            RadioViewModel(serviceConnection = get())
        }
    }
}