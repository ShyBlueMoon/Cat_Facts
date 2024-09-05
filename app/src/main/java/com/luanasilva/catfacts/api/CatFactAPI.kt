package com.luanasilva.catfacts.api

import com.luanasilva.catfacts.model.CatFact
import retrofit2.Response
import retrofit2.http.GET

interface CatFactAPI {

    @GET("fact")
    suspend fun  recoveryCatFact() :Response<CatFact>
}