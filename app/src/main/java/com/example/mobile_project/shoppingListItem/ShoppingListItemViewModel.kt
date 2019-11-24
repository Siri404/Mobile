package com.example.mobile_project.shoppingListItem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mobile_project.database.ShoppingListItemDao
import com.example.mobile_project.model.ShoppingList
import com.example.mobile_project.model.ShoppingListItem
import kotlinx.coroutines.*

class ShoppingListItemViewModel(
    val database: ShoppingListItemDao,
    val shoppingListId: Long,
    application: Application) : AndroidViewModel(application){

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope( Dispatchers.Main + viewModelJob)

    val items = database.getAll(shoppingListId)

    fun addItem(itemName: String, price:Int){
        uiScope.launch {
            val newItem = ShoppingListItem(listId = shoppingListId, itemName = itemName, price = price)
            insert(newItem)
        }
    }

    private suspend fun insert(shoppingListItem: ShoppingListItem){
        withContext(Dispatchers.IO){
            database.insert(shoppingListItem)
        }
    }

    fun updateItem(newItemName: String, itemId: Long, listId: Long, price: Int){
        uiScope.launch {
            val shoppingListItem = ShoppingListItem(itemId, listId, newItemName, price)
            update(shoppingListItem)
        }
    }

    private suspend fun update(shoppingListItem: ShoppingListItem){
        withContext(Dispatchers.IO){
            database.update(shoppingListItem)
        }
    }

    fun removeItem(itemId: Long){
        uiScope.launch {
            delete(itemId)
        }
    }

    private suspend fun delete(itemId: Long){
        withContext(Dispatchers.IO){
            database.delete(database.get(itemId))
        }
    }

}