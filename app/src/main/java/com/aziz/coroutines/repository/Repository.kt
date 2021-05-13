package com.aziz.coroutines.repository

import androidx.lifecycle.MutableLiveData
import com.aziz.coroutines.api.ApiClient
import com.aziz.coroutines.models.Currency
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext

class Repository {

    var list: MutableLiveData<List<Currency>> = MutableLiveData()

    companion object {
        var repository: Repository? = null

        fun getInstance(): Repository {
            if (repository == null) {
                repository = Repository()
            }
            return repository as Repository
        }
    }

    suspend fun getData(date: String): MutableLiveData<List<Currency>> {
        val response = when {
            date == "" -> ApiClient.api.getAllCurrencies()
            else -> ApiClient.api.getCurrencyAtDate(date)
        }
        if (response.isSuccessful) {
            withContext(Main) {
                list.value = response.body()
            }
        }
        return list
    }


}