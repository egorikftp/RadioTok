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
import org.koin.dsl.bind
import org.koin.dsl.factory
import org.koin.dsl.module

val dataSourceModule = module {

    factory<EntitySourceFactory>() bind IEntitySourceFactory::class

    factory<ChangedLatelyDataSource>() bind IChangedLatelyDataSource::class
    factory<LocalStationsDataSource>() bind ILocalStationsDataSource::class
    factory<PlayingNowDataSource>() bind IPlayingNowDataSource::class
    factory<TopClicksDataSource>() bind ITopClicksDataSource::class
    factory<TopVoteDataSource>() bind ITopVoteDataSource::class

    factory<TagsDataSource>() bind ITagsDataSource::class
    factory<LanguagesDataSource>() bind ILanguagesDataSource::class
    factory<CountriesDataSource>() bind ICountriesDataSource::class

    factory<RadioInfoDataSource>() bind IRadioInfoDataSource::class
    factory<AllStationsDataSource>() bind IAllStationsDataSource::class
}