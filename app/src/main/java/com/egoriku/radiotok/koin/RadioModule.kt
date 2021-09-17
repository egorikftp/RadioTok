package com.egoriku.radiotok.koin

import com.egoriku.radiotok.common.provider.IBitmapProvider
import com.egoriku.radiotok.common.provider.IStringResourceProvider
import com.egoriku.radiotok.data.datasource.RadioDnsServer
import com.egoriku.radiotok.data.repository.RadioFetchNetworkRepository
import com.egoriku.radiotok.data.retrofit.ApiEndpoint
import com.egoriku.radiotok.data.retrofit.HostSelectionInterceptor
import com.egoriku.radiotok.domain.common.internal.BitmapProvider
import com.egoriku.radiotok.domain.common.internal.StringResourceProvider
import com.egoriku.radiotok.domain.datasource.IRadioDnsServer
import com.egoriku.radiotok.domain.mediator.RadioCacheMediator
import com.egoriku.radiotok.domain.repository.IRadioFetchNetworkRepository
import com.egoriku.radiotok.domain.usecase.FeedUseCase
import com.egoriku.radiotok.domain.usecase.IRadioCacheUseCase
import com.egoriku.radiotok.domain.usecase.PlaylistUseCase
import com.egoriku.radiotok.domain.usecase.RadioCacheUseCase
import com.egoriku.radiotok.presentation.IMusicServiceConnection
import com.egoriku.radiotok.presentation.MainActivity
import com.egoriku.radiotok.presentation.RadioServiceConnection
import com.egoriku.radiotok.presentation.RadioViewModel
import com.egoriku.radiotok.presentation.screen.feed.FeedViewModel
import com.egoriku.radiotok.presentation.screen.playlist.PlaylistViewModel
import com.egoriku.radiotok.presentation.screen.settings.SettingsViewModel
import com.egoriku.radiotok.radioplayer.data.mediator.IRadioCacheMediator
import com.egoriku.radiotok.util.BackupManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.single

val appScope = module {
    single<BitmapProvider>() bind IBitmapProvider::class
    single<StringResourceProvider>() bind IStringResourceProvider::class
    single {
        BackupManager(
            context = androidContext(),
            db = get()
        )
    }
}

val network = module {
    single { ApiEndpoint(hostSelectionInterceptor = get()).create() }
    single { HostSelectionInterceptor(radioDnsServer = get()) }
}

val radioModule = module {
    factory<IRadioDnsServer> { RadioDnsServer() }

    factory<IRadioFetchNetworkRepository> {
        RadioFetchNetworkRepository(allStationsDataSource = get())
    }

    factory<IRadioCacheUseCase> {
        RadioCacheUseCase(
            radioTokDb = get(),
            radioFetchNetworkRepository = get()
        )
    }

    single<IRadioCacheMediator> {
        RadioCacheMediator(
            radioCacheUseCase = get(),
            mediaItemRepository = get(),
            mediaMetadataRepository = get(),
            currentRadioQueueHolder = get()
        )
    }

    single<IMusicServiceConnection> {
        RadioServiceConnection(context = androidContext())
    }

    scope<MainActivity> {
        viewModel {
            RadioViewModel(serviceConnection = get())
        }
    }
}

val feedScreenModule = module {
    factory {
        FeedUseCase(
            tagsDataSource = get(),
            languagesDataSource = get(),
            countriesDataSource = get(),
            stringResource = get()
        )
    }

    viewModel {
        FeedViewModel(serviceConnection = get(), feedUseCase = get())
    }
}

val playlistScreenModule = module {
    factory {
        PlaylistUseCase(
            db = get(),
            stringResource = get(),
            radioInfoDataSource = get(),
            localStationsDataSource = get(),
        )
    }

    viewModel {
        PlaylistViewModel(playlistUseCase = get())
    }
}

val settingsScreenModule = module {
    viewModel {
        SettingsViewModel(backupManager = get())
    }
}