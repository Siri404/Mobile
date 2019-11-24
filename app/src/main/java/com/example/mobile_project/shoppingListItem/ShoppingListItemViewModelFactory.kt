package com.example.mobile_project.shoppingListItem

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_project.database.ShoppingListItemDao
import java.lang.IllegalArgumentException

class ShoppingListItemViewModelFactory(
    private val dataSource: ShoppingListItemDao,
    private val shoppingListId: Long,
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingListItemViewModel::class.java)){
            return ShoppingListItemViewModel(dataSource, shoppingListId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}