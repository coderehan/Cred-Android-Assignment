package com.rehan.credandroidassignment.api

import com.rehan.credandroidassignment.models.CredModel
import retrofit2.Response
import retrofit2.http.GET

interface CredAPI {

    @GET("success_case")
    suspend fun getSuccessCase(): Response<CredModel>

    @GET("failure_case")
    suspend fun getFailureCase(): Response<CredModel>

}