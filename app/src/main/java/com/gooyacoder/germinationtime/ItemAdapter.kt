package com.gooyacoder.germinationtime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ItemAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.plantName)
        val date: TextView = view.findViewById(R.id.startDate)
        val image: ImageView = view.findViewById(R.id.plantImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.textView.text = currentItem.title
        val g_date = GerminationDate()
        val persian_date = g_date.dateToPersian(g_date.stringToDate(currentItem.startDate))
        holder.date.text = persian_date.longDateString
        holder.image.setImageBitmap(DbBitmapUtility.getImage(currentItem.image))
    }

    override fun getItemCount() = itemList.size
}
