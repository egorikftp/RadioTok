package com.egoriku.radiotok.data.datasource

import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.domain.datasource.IRadioDnsServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress

private const val DNS_LOOKUP_SERVER = "all.api.radio-browser.info"
private const val FALLBACK_SERVER = "de1.api.radio-browser.info"

internal class RadioDnsServer : IRadioDnsServer {

    override suspend fun lookup() = withContext(Dispatchers.IO) {
        val listResult = mutableListOf<String>()

        runCatching {
            InetAddress
                .getAllByName(DNS_LOOKUP_SERVER)
                .map { item ->
                    val currentHostAddress = item.hostAddress
                    val name = item.canonicalHostName

                    if (name != DNS_LOOKUP_SERVER && name != currentHostAddress) {
                        listResult.add(name)
                    }
                }
        }

        if (listResult.isEmpty()) {
            listResult.add(FALLBACK_SERVER)
        }

        logD("DNS, Found servers: $listResult")

        listResult
    }
}