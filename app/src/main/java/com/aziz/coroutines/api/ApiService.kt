package com.aziz.coroutines.api

import com.aziz.coroutines.models.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("json")
    suspend fun getAllCurrencies(): Response<List<Currency>>

    @GET("json/all/{date}/")
    suspend fun getCurrencyAtDate(@Path("date") date: String): Response<List<Currency>>

}