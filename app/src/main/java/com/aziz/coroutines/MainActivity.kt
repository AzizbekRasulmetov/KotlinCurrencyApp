package com.aziz.coroutines

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
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
    private lateinit var searchList: MutableList<Currency>
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.progressBar.visibility = View.VISIBLE
        buildRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //Fill RecyclerView with data
        getData("")

    }

    fun buildRecyclerView() {
        currencyList = ArrayList()
        searchList = ArrayList()
        val animation = AnimationUtils.loadLayoutAnimation(this, R.anim.anim_layout)
        adapter = CurrencyAdapter(searchList)
        binding.recyclerView.setLayoutAnimation(animation)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    //Fetch data from web service. If date is given, search with date otherwise search current date data
    fun getData(date: String) {
        when (date) {
            "" -> { viewModel.getData("") }
            else -> { viewModel.getData(date) }
        }
        viewModel.currencyList.observe(this, androidx.lifecycle.Observer {
            currencyList.clear()
            currencyList.addAll(it)
            searchList.addAll(currencyList)
            adapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.INVISIBLE
        })
        binding.recyclerView.scheduleLayoutAnimation()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.meni_main, menu)
        val menuItem = menu!!.findItem(R.id.search_action)
        if (menuItem != null) {
            search(menuItem)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.calendar_action -> showDatePickerDialog()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun search(menuItem: MenuItem) {
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                if (newText!!.isNotEmpty()) {
                    val searchTxt = newText.toLowerCase(Locale.getDefault())
                    currencyList.forEach {
                        val country: String
                        when (Locale.getDefault().toString()) {
                            "ru_RU" -> country = it.CcyNm_RU
                            else -> country = it.CcyNm_UZ
                        }
                        if (country.toLowerCase(Locale.getDefault()).contains(searchTxt)) {
                            searchList.add(it)
                        }
                    }
                } else {
                    searchList.addAll(currencyList)
                }
                adapter.notifyDataSetChanged()
                return true
            }
        })
    }


}