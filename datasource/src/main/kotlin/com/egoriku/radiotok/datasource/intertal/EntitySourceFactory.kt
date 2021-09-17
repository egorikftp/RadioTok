package com.egoriku.radiotok.datasource.intertal

import com.egoriku.radiotok.datasource.IEntitySourceFactory
import com.egoriku.radiotok.datasource.datasource.IRadioInfoDataSource
import com.egoriku.radiotok.datasource.datasource.metadata.ICountriesDataSource
import com.egoriku.radiotok.datasource.datasource.metadata.ILanguagesDataSource
import com.egoriku.radiotok.datasource.datasource.metadata.ITagsDataSource
import com.egoriku.radiotok.datasource.datasource.playlist.*
import com.egoriku.radiotok.datasource.entity.params.MetadataParams
import com.egoriku.radiotok.datasource.entity.params.MetadataParams.*
import com.egoriku.radiotok.datasource.entity.params.PlaylistParams
import com.egoriku.radiotok.datasource.entity.params.PlaylistParams.*

internal class EntitySourceFactory(
    private val changedLatelyDataSource: IChangedLatelyDataSource,
    private val localStationsDataSource: ILocalStationsDataSource,
    private val playingNowDataSource: IPlayingNowDataSource,
    private val topClicksDataSource: ITopClicksDataSource,
    private val topVoteDataSource: ITopVoteDataSource,
    private val tagsDataSource: ITagsDataSource,
    private val languagesDataSource: ILanguagesDataSource,
    private val countriesDataSource: ICountriesDataSource,
    private val radioInfoDataSource: IRadioInfoDataSource,
) : IEntitySourceFactory {

    override suspend fun loadBy(params: PlaylistParams) = when (params) {
        ChangedLately -> changedLatelyDataSource.load()
        LocalStations -> localStationsDataSource.load()
        PlayingNow -> playingNowDataSource.load()
        TopClicks -> topClicksDataSource.load()
        TopVote -> topVoteDataSource.load()
    }

    override suspend fun loadBy(params: MetadataParams) = when (params) {
        Tags -> tagsDataSource.load()
        Languages -> languagesDataSource.load()
        Countries -> countriesDataSource.load()
    }

    override suspend fun loadByIds(ids: List<String>) = radioInfoDataSource.loadByIds(ids)
}