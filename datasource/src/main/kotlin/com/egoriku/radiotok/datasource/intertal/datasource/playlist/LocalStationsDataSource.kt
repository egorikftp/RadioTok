package com.egoriku.radiotok.datasource.intertal.datasource.playlist

import android.content.Context
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.common.ext.telephonyManager
import com.egoriku.radiotok.datasource.datasource.playlist.ILocalStationsDataSource
import com.egoriku.radiotok.datasource.entity.RadioEntity
import com.egoriku.radiotok.datasource.intertal.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class LocalStationsDataSource(
    context: Context,
    private val api: Api
) : ILocalStationsDataSource {

    private val telephonyManager = context.telephonyManager

    private var result: List<RadioEntity>? = null

    override suspend fun load(): List<RadioEntity> {
        val countryCode = getCountryCode()

        return when {
            countryCode.isNullOrEmpty() -> emptyList()
            else -> runCatching {
                withContext(Dispatchers.IO) {
                    result ?: api.byCountyCode(countryCode = countryCode).also {
                        result = it
                    }
                }
            }.getOrDefault(emptyList())
        }
    }

    private fun getCountryCode(): String? {
        var countryCode = telephonyManager.networkCountryIso

        if (countryCode == null) {
            countryCode = telephonyManager.simCountryIso
        }

        return when {
            countryCode != null -> {
                if (countryCode.length == 2) {
                    logD("countrycode: $countryCode")
                    countryCode
                } else {
                    logD("countrycode length != 2")
                    null
                }
            }
            else -> {
                logD("device countrycode and sim countrycode are null")
                null
            }
        }
    }
}