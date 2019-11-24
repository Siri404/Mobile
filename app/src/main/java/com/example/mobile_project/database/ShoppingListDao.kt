package com.example.mobile_project.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mobile_project.model.ShoppingList

@Dao
interface ShoppingListDao {
    @Insert
    fun insert(shoppingList: ShoppingList)

    @Update
    fun update(shoppingList: ShoppingList)

    @Query("SELECT * from shopping_list_table WHERE listId = :key")
    fun get(key: Long): ShoppingList

    @Delete
    fun delete(shoppingList: ShoppingList)

    @Query("SELECT * from shopping_list_table ORDER BY listId DESC")
    fun getAll(): LiveData<List<ShoppingList>>
}