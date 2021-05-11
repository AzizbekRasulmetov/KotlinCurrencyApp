package com.aziz.coroutines

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aziz.coroutines.adapters.CurrencyAdapter
import com.aziz.coroutines.databinding.ActivityMainBinding
import com.aziz.coroutines.models.Currency
import com.aziz.coroutines.viewmodel.MainViewModel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CurrencyAdapter
    private lateinit var currencyList: MutableList<Currency>
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        buildRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        getData("")
        binding.datePickBtn.setOnClickListener {
            showDatePickerDialog()
        }


    }

    fun buildRecyclerView() {
        currencyList = ArrayList()
        adapter = CurrencyAdapter(currencyList)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    fun getData(date: String) {
        Log.i("MyTag", "main getData")
        when(date) {
            "" -> {
                viewModel.getData("").observe(this,
                    {
                        currencyList.clear()
                        currencyList.addAll(it)
                        adapter.notifyDataSetChanged()
                    })
            }
            else -> {
                viewModel.getData(date).observe(this, {
                    currencyList.clear()
                    currencyList.addAll(it)
                    adapter.notifyDataSetChanged()
                })
            }
        }
        adapter.notifyDataSetChanged()

    }

    fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONDAY),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = "${year}-${month}-${dayOfMonth}"
        binding.dateTxt.text = selectedDate
        getData(selectedDate)
    }




















//        btnCount.setOnClickListener {
//            tvCount.text = count++.toString()
//        }
//        btnDownloadUserData.setOnClickListener {
//
//            CoroutineScope(IO).launch {
//                Log.i("MyTag", "Calculation is started")
//                val stock1 = async{ getStock1() }
//                val stock2 = async{ getStock2() }
//                val total = stock1.await() + stock2.await()
//                Log.i("MyTag", "Total: $total")
//            }
////            CoroutineScope(Dispatchers.IO).launch {
////                downloadUserData()
////            }
////            CoroutineScope(Dispatchers.Main).launch {
////                downloadUserData()
////            }
//        }
//    }
//
//    private suspend fun downloadUserData() {
//        for (i in 1..200000) {
//            //Log.i("MyTag", "Downloading user $i in ${Thread.currentThread().name}")
//            withContext(Dispatchers.Main){
//                tvUserMessage.text = "Downloading user $i in ${Thread.currentThread().name}"
//            }
//        }
//    }
//
//    private suspend fun getStock1(): Int {
//        delay(10000)
//        Log.i("MyTag", "Stock 1 is returned")
//        return 55000
//    }
//
//    private suspend fun getStock2(): Int {
//        delay(6000)
//        Log.i("MyTag", "Stock 2 is returned")
//        return 35000
//    }


}