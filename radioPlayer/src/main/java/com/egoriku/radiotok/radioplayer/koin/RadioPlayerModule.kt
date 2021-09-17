package com.egoriku.radiotok.radioplayer.koin

import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.egoriku.radiotok.radioplayer.data.RadioStateMediator
import com.egoriku.radiotok.radioplayer.repository.IMediaItemRepository
import com.egoriku.radiotok.radioplayer.repository.IMediaMetadataRepository
import com.egoriku.radiotok.radioplayer.repository.MediaItemRepository
import com.egoriku.radiotok.radioplayer.repository.MediaMetadataRepository
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.factory
import org.koin.dsl.module

val exoPlayerModule = module {
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
}

val radioPlayerModule = module {
    factory<MediaItemRepository>() bind IMediaItemRepository::class
    factory<MediaMetadataRepository>() bind IMediaMetadataRepository::class

    single {
        RadioStateMediator(radioTokDb = get())
    }

    single {
        CurrentRadioQueueHolder(
            defaultHttpDataSourceFactory = get()
        )
    }
}