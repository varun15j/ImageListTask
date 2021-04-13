package com.nayan.varun.di.module

import android.content.Context
import com.nayan.varun.App
import com.nayan.varun.BuildConfig
import com.nayan.varun.data.local.RepoDatabase
import com.nayan.varun.data.remote.ApiService
import com.nayan.varun.util.ImageUtil
import com.nayan.varun.util.NetworkUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import android.arch.persistence.room.Room



@Module
class AppModule(var app: App) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(builder: OkHttpClient.Builder,
                            loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        return builder.addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient, gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @Singleton
    fun provideApiService(builder: Retrofit.Builder): ApiService {
        return builder
                .baseUrl(BuildConfig.github_base_url)
                .build()
                .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkUtil(context: Context): NetworkUtil {
        return NetworkUtil(context)
    }

    @Provides
    @Singleton
    fun provideImageUtil(context: Context): ImageUtil {
        return ImageUtil(context)
    }

    @Provides
    @Singleton
    fun provideRepoDatabase(context: Context): RepoDatabase {
        return Room.databaseBuilder(context,
                RepoDatabase::class.java, "repo.db").build()
    }
}