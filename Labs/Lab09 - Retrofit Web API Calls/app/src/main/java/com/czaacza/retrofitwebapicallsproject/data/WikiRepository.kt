package com.czaacza.retrofitwebapicallsproject.data

import com.czaacza.retrofitwebapicallsproject.data.search.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WikiRepository {

    private val service = MyApi.service

    suspend fun hitCountCheck(name: String): SearchResult {
        val retrievedData = withContext(Dispatchers.IO) {
            service.searchItem(name)
        }
        return retrievedData
    }

}
