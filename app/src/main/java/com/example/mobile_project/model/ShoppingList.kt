package com.example.mobile_project.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_table", indices = [Index(value = ["listId"], name = "name")])
data class ShoppingList(
    var listName: String = "",
    @PrimaryKey(autoGenerate = true)
    var listId: Long = 0L

)