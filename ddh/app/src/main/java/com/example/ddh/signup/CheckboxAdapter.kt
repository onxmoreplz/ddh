package com.example.ddh.signup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.ddh.R

class CheckboxAdapter(private val context: Context) : RecyclerView.Adapter<CheckboxAdapter.ViewHolder>() {

    val agreementList: List<String> = listOf(
        "이용약관 동의",
        "개인정보 취급방침 동의",
        "마케팅 정보 수신 동의"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckboxAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_check_box, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckboxAdapter.ViewHolder, position: Int) {
        holder.bind(agreementList[position])
    }

    override fun getItemCount(): Int = agreementList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val checkbox: CheckBox = itemView.findViewById(R.id.cb_name)

        fun bind(item: String) {
            checkbox.text = item
        }
    }
}