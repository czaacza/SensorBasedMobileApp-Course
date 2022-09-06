package com.czaacza.retrofitwebapicallsproject.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czaacza.retrofitwebapicallsproject.data.WikiRepository
import com.czaacza.retrofitwebapicallsproject.data.search.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel() : ViewModel() {
    private val repository: WikiRepository = WikiRepository()
    val changeNotifier = MutableLiveData<Int>()


    fun getHits(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val serverResp: SearchResult = repository.hitCountCheck(name)
            changeNotifier.postValue(serverResp.query.searchinfo.totalhits)
        }
    }
}