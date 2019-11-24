package com.example.mobile_project.shoppingList

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.R
import com.example.mobile_project.databinding.ShoppingListViewBinding
import com.example.mobile_project.model.ShoppingList
import kotlin.properties.Delegates


class ShoppingListAdapter(val clickListener: ListClickListener, val menuListener: MenuOnClickListener): ListAdapter<ShoppingList ,ShoppingListAdapter.ViewHolder>(ListDiffCalback()) {
    class ViewHolder(val binding: ShoppingListViewBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(item: ShoppingList, clickListener: ListClickListener, menuListener: MenuOnClickListener){
            binding.item = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
            binding.itemNameTextView.text = item.listName
            binding.optionsButton.setOnClickListener {
                //creating a popup menu
                val popup = PopupMenu(binding.root.context, binding.optionsButton)
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu)
                //adding click listener
                menuListener.listId = item.listId
                popup.setOnMenuItemClickListener(menuListener)
                //displaying the popup
                popup.show()
            }
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ShoppingListViewBinding.inflate(layoutInflater,
                    parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener, menuListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ListDiffCalback: DiffUtil.ItemCallback<ShoppingList>(){
    override fun areItemsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
        return oldItem.listId == newItem.listId
    }

    override fun areContentsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
        return oldItem == newItem
    }

}

class ListClickListener(val clickListener:(listId: Long) -> Unit){
    fun onClick(shoppingList: ShoppingList) = clickListener(shoppingList.listId)
}

class MenuOnClickListener(val clickListener: (item:MenuItem?, listId: Long) -> Boolean): PopupMenu.OnMenuItemClickListener {
    var listId by Delegates.notNull<Long>()
    override fun onMenuItemClick(item: MenuItem?): Boolean = clickListener(item, listId)
}