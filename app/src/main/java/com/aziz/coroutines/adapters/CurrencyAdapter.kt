package com.aziz.coroutines.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aziz.coroutines.R
import com.aziz.coroutines.models.Currency
import kotlinx.android.synthetic.main.currency_item.view.*
import java.util.*

class CurrencyAdapter(private val list: List<Currency>) :
    RecyclerView.Adapter<CurrencyAdapter.MyViewHolder>() {

    init {
        setHasStableIds(true)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val lang = Locale.getDefault().toString()

        val flagMap = hashMapOf(
            "USD" to R.drawable.usa,
            "EUR" to R.drawable.eur,
            "RUB" to R.drawable.russ,
            "JPY" to R.drawable.japan,
            "CNY" to R.drawable.china,
            "CAD" to R.drawable.canada,
            "INR" to R.drawable.india,
            "AUD" to R.drawable.australia,
            "KGS" to R.drawable.kgz,
            "KRW" to R.drawable.korea,
            "KZT" to R.drawable.kgz,
            "TJS" to R.drawable.tjk
        )

        fun onBind(currency: Currency) {
            when (lang) {
                "ru_RU" -> itemView.currency_name_txt.text = currency.CcyNm_RU
                else -> itemView.currency_name_txt.text = currency.CcyNm_UZ
            }
            itemView.rate_txt.text = "${currency.Ccy} = ${currency.Rate} so'm"
            if (flagMap.get(currency.Ccy) != null) {
                itemView.country_flag.setImageResource(flagMap.get(currency.Ccy)!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}