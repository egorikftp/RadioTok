package com.egoriku.radiotok.data.retrofit

import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.domain.datasource.IRadioDnsServer
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HostSelectionInterceptor(
    private val radioDnsServer: IRadioDnsServer
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return if (request.url.host == "github.com") {
            runBlocking {
                val host = radioDnsServer.lookup().random()
                logD("base host: $host")

                val newUrl = request.url.newBuilder().host(host).build()

                chain.proceed(request.newBuilder().url(newUrl).build())
            }
        } else {
            chain.proceed(request)
        }
    }
}