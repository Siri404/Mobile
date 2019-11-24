package com.example.mobile_project.shoppingList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mobile_project.database.ShoppingListDao
import com.example.mobile_project.model.ShoppingList
import kotlinx.coroutines.*

class ShoppingListViewModel(
    val database: ShoppingListDao,
    application: Application) : AndroidViewModel(application){

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope( Dispatchers.Main + viewModelJob)

    val shoppingLists = database.getAll()

    fun addList(listName: String){
        uiScope.launch {
            val newShoppingList = ShoppingList(listName)
            insert(newShoppingList)
        }
    }

    private suspend fun insert(list: ShoppingList){
        withContext(Dispatchers.IO){
            database.insert(list)
        }
    }

    fun updateList(newListName: String, listId: Long){
        uiScope.launch {
            val shoppingList = ShoppingList(newListName, listId)
            update(shoppingList)
        }
    }

    private suspend fun update(shoppingList: ShoppingList){
        withContext(Dispatchers.IO){
            database.update(shoppingList)
        }
    }

    fun removeList(listId: Long){
        uiScope.launch {
            delete(listId)
        }
    }

    private suspend fun delete(listId: Long){
        withContext(Dispatchers.IO){
            database.delete(database.get(listId))
        }
    }

    private val _navigateToShoppingListItem = MutableLiveData<Long>()
    val navigateToShoppingListItem
        get() = _navigateToShoppingListItem

    fun onListClicked(id: Long){
        _navigateToShoppingListItem.value = id
    }

    fun onShoppingListItemNavigated(){
        _navigateToShoppingListItem.value = null
    }

}
