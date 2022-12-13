package com.cutecats.android.di.module

import android.content.Context
import com.cutecats.android.BuildConfig
import com.cutecats.android.data.network.CatsApiService
import com.cutecats.android.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @Named("okhttp_client")
    fun providesOKHttpClient(context: Context): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        var mOkHttpClient = OkHttpClient.Builder()

        mOkHttpClient.apply {
            addInterceptor(httpLoggingInterceptor)
            retryOnConnectionFailure(true)
            readTimeout(30, TimeUnit.SECONDS)
            connectTimeout(20, TimeUnit.SECONDS)
        }

        return mOkHttpClient.build()
    }

    @Provides
    @Singleton
    @Named("retrofit_client")
    fun providesRetrofit(@Named("okhttp_client") okHttpClient: OkHttpClient, gsonBuilder: GsonBuilder): Retrofit {
        var retrofit = Retrofit.Builder()

        retrofit.apply {

            baseUrl(Constants.BASE_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
        }

        return retrofit.build()
    }

    @Provides
    @Singleton
    fun providesNonAuthApiService(@Named("retrofit_client")retrofit: Retrofit): CatsApiService {
        return retrofit.create(CatsApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()

        return gsonBuilder
    }
}