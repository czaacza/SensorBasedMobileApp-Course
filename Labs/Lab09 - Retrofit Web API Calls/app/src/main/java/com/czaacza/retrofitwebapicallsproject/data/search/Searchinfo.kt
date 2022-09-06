package com.czaacza.retrofitwebapicallsproject.data.search

data class Searchinfo(
    val suggestion: String,
    val suggestionsnippet: String,
    val totalhits: Int
)