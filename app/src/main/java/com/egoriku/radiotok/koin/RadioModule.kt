package com.egoriku.radiotok.koin

import com.egoriku.radiotok.MainActivity
import com.egoriku.radiotok.data.Api
import com.egoriku.radiotok.data.datasource.IRadioDataSource
import com.egoriku.radiotok.data.datasource.IStationsDataSource
import com.egoriku.radiotok.data.datasource.RadioServicesDataSource
import com.egoriku.radiotok.data.datasource.StationsDataSource
import com.egoriku.radiotok.viewmodel.RadioViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val radioModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<Api> {
        get<Retrofit>().create(Api::class.java)
    }

    scope<MainActivity> {
        scoped<IRadioDataSource> { RadioServicesDataSource() }
        scoped<IStationsDataSource> { StationsDataSource(api = get()) }

        viewModel {
            RadioViewModel(
                radioDataSource = get(),
                stationsDataSource = get()
            )
        }
    }
}