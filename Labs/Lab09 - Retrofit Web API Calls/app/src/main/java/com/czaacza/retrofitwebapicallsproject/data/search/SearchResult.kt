package com.czaacza.retrofitwebapicallsproject.data.search

data class SearchResult(
    val batchcomplete: String,
    val `continue`: Continue,
    val query: Query
)