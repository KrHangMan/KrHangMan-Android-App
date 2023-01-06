package com.hang.android.krhangman.db

import androidx.room.TypeConverter
import java.util.*

class HangmanTypeConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID?):String?{
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuid:String?):UUID?{
        return UUID.fromString(uuid)
    }

}