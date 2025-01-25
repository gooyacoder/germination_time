package com.gooyacoder.germinationtime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ItemAdapter(private val itemList: List<Item>,
                  private val itemClickListener: OnItemClickListener,
                  private val listener: OnItemLongClickListener) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Item)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(item: Item)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.plantName)
        val date: TextView = view.findViewById(R.id.startDate)
        val image: ImageView = view.findViewById(R.id.plantImage)
        fun bind(item: Item, clickListener: OnItemClickListener) {
            textView.text = item.title
            val g_date = GerminationDate()
            val persian_date = g_date.dateToPersian(g_date.stringToDate(item.startDate))
            date.text = persian_date.longDateString
            image.setImageBitmap(DbBitmapUtility.getImage(item.image))
            itemView.setOnClickListener {
                clickListener.onItemClick(item)
            }
        }
        init {
            itemView.setOnLongClickListener {
                listener.onItemLongClick(itemList[adapterPosition])
                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position], itemClickListener)
    }

    override fun getItemCount() = itemList.size
}
