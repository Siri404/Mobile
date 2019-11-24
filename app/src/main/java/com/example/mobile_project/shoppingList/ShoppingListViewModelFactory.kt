package com.example.mobile_project.shoppingList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_project.database.ShoppingListDao
import java.lang.IllegalArgumentException

class ShoppingListViewModelFactory(
    private val dataSource: ShoppingListDao,
    private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingListViewModel::class.java)){
            return ShoppingListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}