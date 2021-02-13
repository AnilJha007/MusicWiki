package com.mobile.musicwiki.ui.genredetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.musicwiki.R
import com.mobile.musicwiki.utils.setImage
import com.mobile.musicwiki.utils.setTextOrHide
import kotlinx.android.synthetic.main.item_list.view.*

data class Item(val title: String? = null, val subTitle: String? = null, val url: String? = null)

class ItemAdapter(
    private val itemClickListener: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val items = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) {
            with(itemView) {
                item.apply {
                    tvTitle.setTextOrHide(title)
                    tvSubtitle.setTextOrHide(subTitle)
                    ivItem.setImage(url)
                }
                setOnClickListener {
                    itemClickListener(item)
                }
            }
        }
    }

    fun updateData(data: ArrayList<Item>) {
        items.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }
}