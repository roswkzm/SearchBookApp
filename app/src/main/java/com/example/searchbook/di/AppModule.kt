package com.example.searchbook.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.work.WorkManager
import com.example.searchbook.data.db.AppDatabase
import com.example.searchbook.network.BookSearchService
import com.example.searchbook.util.Constants.BASE_URL
import com.example.searchbook.util.Constants.DATASTORE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideBookSearchService(retrofit: Retrofit): BookSearchService {
        return retrofit.create(BookSearchService::class.java)
    }

    // Room
    @Provides
    @Singleton
    fun providesBookSearchDatabase(@ApplicationContext context : Context) : AppDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "favorite-books")
            .build()

    // DataStore
    @Provides
    @Singleton
    fun providesPreferencesDataStore(@ApplicationContext context: Context) : DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {context.preferencesDataStoreFile(DATASTORE_NAME)}
        )

    // WorkManager
    @Provides
    @Singleton
    fun providesWorkManager(@ApplicationContext context: Context) : WorkManager =
        WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun providesCacheDeleteResult() : String = "Cache has delete by Hilt"
}