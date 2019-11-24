package com.example.mobile_project.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mobile_project.model.ShoppingListItem

@Dao
interface ShoppingListItemDao {
    @Insert
    fun insert(shoppingListItem: ShoppingListItem)

    @Update
    fun update(shoppingListItem: ShoppingListItem)

    @Query("SELECT * from shopping_list_item_table WHERE itemId = :key")
    fun get(key: Long): ShoppingListItem

    @Delete
    fun delete(shoppingListItem: ShoppingListItem)

    @Query("SELECT * from shopping_list_item_table WHERE listId = :shoppingListId ORDER BY itemId DESC")
    fun getAll(shoppingListId: Long): LiveData<List<ShoppingListItem>>
}