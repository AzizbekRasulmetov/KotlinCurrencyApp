package com.aziz.coroutines.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aziz.coroutines.models.Currency
import com.aziz.coroutines.repository.Repository

class MainViewModel: ViewModel() {

    private lateinit var currencyList: MutableLiveData<List<Currency>>
    var repository = Repository.getInstance()

    fun getData(date: String): MutableLiveData<List<Currency>> {
        currencyList = repository.getData(date)
        return currencyList
    }

}