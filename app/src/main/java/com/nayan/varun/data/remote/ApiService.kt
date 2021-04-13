package com.nayan.varun.data.remote

import com.nayan.varun.data.model.response.RepoResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("v3/c4ab4c1c-9a55-4174-9ed2-cbbe0738eedf")
    fun fetchRepos(): Call<RepoResponse>
}