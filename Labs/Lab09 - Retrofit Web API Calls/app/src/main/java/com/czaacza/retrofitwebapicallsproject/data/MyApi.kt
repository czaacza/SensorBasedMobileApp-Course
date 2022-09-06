package com.czaacza.retrofitwebapicallsproject.data

import com.czaacza.retrofitwebapicallsproject.data.search.SearchResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object MyApi {

    const val URL = "https://en.wikipedia.org"

    interface Service {
        @GET("/w/api.php?action=query&format=json&list=search")
        suspend fun searchItem(@Query("srsearch") action: String): SearchResult
    }

    private val retrofit = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(Service::class.java)
}
