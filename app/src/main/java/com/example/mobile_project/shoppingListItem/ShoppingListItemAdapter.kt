package com.example.mobile_project.shoppingListItem

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.R
import com.example.mobile_project.databinding.ShoppingListItemViewBinding
import com.example.mobile_project.model.ShoppingListItem
import kotlin.properties.Delegates

class ShoppingListItemAdapter(val itemClickListener: ItemClickListener, val menuListener: MenuOnClickListener):
    androidx.recyclerview.widget.ListAdapter<ShoppingListItem,ShoppingListItemAdapter.ViewHolder>(ItemDiffCalback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, itemClickListener, menuListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(var binding: ShoppingListItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShoppingListItem, clickListener: ItemClickListener, menuListener: MenuOnClickListener){
            binding.item = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
            binding.itemNameTextView.text = item.itemName
            binding.optionsButton.setOnClickListener {
                //creating a popup menu
                val popup = PopupMenu(binding.root.context, binding.optionsButton)
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu)
                //adding click listener
                menuListener.itemId = item.itemId
                popup.setOnMenuItemClickListener(menuListener)
                //displaying the popup
                popup.show()
            }
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ShoppingListItemViewBinding.inflate(layoutInflater,
                    parent, false)

                return ViewHolder(binding)
            }
        }
    }

}

class ItemClickListener(val clickListener:(itemId: Long) -> Unit){
    fun onClick(shoppingListItem: ShoppingListItem) = clickListener(shoppingListItem.itemId)
}

class MenuOnClickListener(val clickListener: (item: MenuItem?, itemId: Long) -> Boolean): PopupMenu.OnMenuItemClickListener {
    var itemId by Delegates.notNull<Long>()
    override fun onMenuItemClick(item: MenuItem?): Boolean = clickListener(item, itemId)
}

class ItemDiffCalback: DiffUtil.ItemCallback<ShoppingListItem>(){
    override fun areItemsTheSame(oldItem: ShoppingListItem, newItem: ShoppingListItem): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: ShoppingListItem, newItem: ShoppingListItem): Boolean {
        return oldItem == newItem
    }

}