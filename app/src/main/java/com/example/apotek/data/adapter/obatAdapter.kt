package com.example.apotek.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.CheckBox
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.apotek.R
import com.example.apotek.data.MainViewModel
import com.example.apotek.data.model.Obat

class obatAdapter(val context: Context, val obat: List<Obat>, val viewModel: MainViewModel): RecyclerView.Adapter<obatAdapter.obatViewHolder>() {
    class obatViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): obatViewHolder {
        return obatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: obatViewHolder, position: Int) {
        val item = obat[position]
        holder.checkBox.text = item.nama_obat
        holder.checkBox.setOnClickListener {
            Thread.sleep(200)
            if(holder.checkBox.isChecked) {
                viewModel.listObat.value!!.add(item.nama_obat.toString())
                viewModel.triggerListObat.value = "true"
                viewModel.price.value = viewModel.price.value!!.toInt() + item.harga.toString().toInt()
            }
            else{
                viewModel.listObat.value!!.remove(item.nama_obat)
                viewModel.triggerListObat.value = "true"
                viewModel.price.value = viewModel.price.value!!.toInt() - item.harga.toString().toInt()
            }
        }
    }

    override fun getItemCount(): Int {
        return obat.size
    }

}