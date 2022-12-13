package com.cutecats.android.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.cutecats.android.CuteCatsApplication
import com.cutecats.android.data.DataRepository
import com.cutecats.android.data.DataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {
    @Provides
    @Singleton
    fun providesApplication(cuteCatsApplication: CuteCatsApplication): CuteCatsApplication {
        return cuteCatsApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideDataRepository(dataRepositoryImpl: DataRepositoryImpl): DataRepository =dataRepositoryImpl

}