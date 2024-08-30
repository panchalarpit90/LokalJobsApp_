package com.example.lokaljobs.di

import android.content.Context
import com.example.lokaljobs.network.apiinterface.ApiInterface
import com.example.lokaljobs.repository.GetRepository
import com.example.lokaljobs.room.JobsDao
import com.example.lokaljobs.room.JobsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModules {

    @Provides
    fun providesBaseUrl(): String = "https://testapi.getlokalapp.com/"

    @Provides
    @Singleton
    fun providesRetrofitBuilder(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)


    @Provides
    @Singleton
    fun providesPostRepository(apiInterface: ApiInterface,jobsDao: JobsDao): GetRepository {
        return GetRepository(apiInterface,jobsDao)

    }
    @Provides
    @Singleton
    fun provideJobsDatabase(@ApplicationContext context: Context): JobsDatabase {
        return JobsDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideJobsDao(jobsDatabase: JobsDatabase): JobsDao {
        return jobsDatabase.getJobDao()
    }
}