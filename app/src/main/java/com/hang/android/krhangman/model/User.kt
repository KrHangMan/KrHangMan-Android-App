package com.hang.android.krhangman.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "User")
data class User(
    @PrimaryKey val id:UUID= UUID.randomUUID(),
    var nickname:String=""
)