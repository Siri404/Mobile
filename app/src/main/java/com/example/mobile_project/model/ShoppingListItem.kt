package com.example.mobile_project.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_item_table",
    foreignKeys = arrayOf(
        ForeignKey(entity = ShoppingList::class,
    parentColumns = arrayOf("listId"),
    childColumns = arrayOf("listId"),
    onDelete = ForeignKey.CASCADE)
    ))
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0L,
    var listId: Long = 0L,
    var itemName: String = "",
    var price: Int = 0
)