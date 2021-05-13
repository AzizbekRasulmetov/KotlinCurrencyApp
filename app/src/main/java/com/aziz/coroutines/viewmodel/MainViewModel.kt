package com.aziz.coroutines.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aziz.coroutines.models.Currency
import com.aziz.coroutines.repository.Repository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    var currencyList: MutableLiveData<List<Currency>> = MutableLiveData()
    var repository = Repository.getInstance()


    fun getData(date: String): MutableLiveData<List<Currency>> {
        viewModelScope.launch {
            var result: MutableLiveData<List<Currency>>
            withContext(IO) {
                result = repository.getData(date)
            }
            currencyList.value = result.value

        }
        return currencyList
    }


}