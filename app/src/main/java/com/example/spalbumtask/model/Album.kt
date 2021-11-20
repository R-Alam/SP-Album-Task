package com.example.spalbumtask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for holding Album details
 */

@Entity
data class Album (val userId: String, @PrimaryKey val id: Int, val title: String)
