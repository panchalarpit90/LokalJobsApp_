package com.example.lokaljobs.network.apiinterface

import com.example.lokaljobs.network.model.Jobs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("common/jobs")
    suspend fun getJobs(@Query("page")page:Int):Jobs
    @GET("common/jobs")
    suspend fun getNewJobs(@Query("page")page:Int): Response<Jobs>

}