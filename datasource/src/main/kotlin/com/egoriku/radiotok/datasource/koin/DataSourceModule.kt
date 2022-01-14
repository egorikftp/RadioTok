package com.egoriku.radiotok.datasource.koin

import com.egoriku.radiotok.datasource.IEntitySourceFactory
import com.egoriku.radiotok.datasource.datasource.IAllStationsDataSource
import com.egoriku.radiotok.datasource.datasource.IRadioInfoDataSource
import com.egoriku.radiotok.datasource.datasource.metadata.ICountriesDataSource
import com.egoriku.radiotok.datasource.datasource.metadata.ILanguagesDataSource
import com.egoriku.radiotok.datasource.datasource.metadata.ITagsDataSource
import com.egoriku.radiotok.datasource.datasource.playlist.*
import com.egoriku.radiotok.datasource.intertal.EntitySourceFactory
import com.egoriku.radiotok.datasource.intertal.datasource.AllStationsDataSource
import com.egoriku.radiotok.datasource.intertal.datasource.RadioInfoDataSource
import com.egoriku.radiotok.datasource.intertal.datasource.metadata.CountriesDataSource
import com.egoriku.radiotok.datasource.intertal.datasource.metadata.LanguagesDataSource
import com.egoriku.radiotok.datasource.intertal.datasource.metadata.TagsDataSource
import com.egoriku.radiotok.datasource.intertal.datasource.playlist.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataSourceModule = module {

    factory<IEntitySourceFactory> {
        EntitySourceFactory(
            changedLatelyDataSource = get(),
            localStationsDataSource = get(),
            playingNowDataSource = get(),
            topClicksDataSource = get(),
            topVoteDataSource = get(),
            tagsDataSource = get(),
            languagesDataSource = get(),
            countriesDataSource = get(),
            radioInfoDataSource = get()
        )
    }

    factory<IChangedLatelyDataSource> { ChangedLatelyDataSource(api = get()) }
    factory<ILocalStationsDataSource> {
        LocalStationsDataSource(
            api = get(),
            context = androidApplication()
        )
    }
    factory<IPlayingNowDataSource> { PlayingNowDataSource(api = get()) }
    factory<ITopClicksDataSource> { TopClicksDataSource(api = get()) }
    factory<ITopVoteDataSource> { TopVoteDataSource(api = get()) }

    factory<ITagsDataSource> { TagsDataSource(api = get()) }
    factory<ILanguagesDataSource> { LanguagesDataSource(api = get()) }
    factory<ICountriesDataSource> { CountriesDataSource(api = get()) }

    factory<IRadioInfoDataSource> { RadioInfoDataSource(api = get()) }
    factory<IAllStationsDataSource> { AllStationsDataSource(api = get()) }
}