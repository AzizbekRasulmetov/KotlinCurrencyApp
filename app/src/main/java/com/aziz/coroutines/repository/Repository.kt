package com.aziz.coroutines.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aziz.coroutines.api.ApiClient
import com.aziz.coroutines.models.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun getData(date: String): MutableLiveData<List<Currency>> {
        CoroutineScope(Dispatchers.IO).launch {

            val response = when {
                date == "" -> ApiClient.api.getAllCurrencies()
                else -> ApiClient.api.getCurrencyAtDate(date)
            }

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    list.value = response.body()
                }
            }
        }
        return list
    }


}