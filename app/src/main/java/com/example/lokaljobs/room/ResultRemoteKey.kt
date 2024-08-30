package com.example.lokaljobs.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResultRemoteKey(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var prev: Int?,
    var next: Int?
)